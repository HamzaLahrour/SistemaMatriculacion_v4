package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import javax.swing.plaf.PanelUI;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Matriculas implements IMatriculas {
    private Connection conexion;
    private static Matriculas instancia;


    public Matriculas(){
        comenzar();
    }


    public static Matriculas getInstancia() {
        if (instancia == null) {
            instancia = new Matriculas();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        MySQL gestorBD= new MySQL();
        this.conexion= gestorBD.establecerConexion();
        if (this.conexion != null) {
            System.out.println("Conexión con la base de datos iniciada en Matriculas.");
        } else {
            System.err.println("ERROR: No se pudo establecer la conexión en Matriculas.");
        }
    }

    @Override
    public void terminar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión a la base de datos cerrada en Matriculas correctamente.");
            } catch (SQLException e) {
                System.err.println("ERROR: No se pudo cerrar la conexión a la base de datos: " + e.getMessage());
            }
        } else {
            System.out.println("ERROR: No hay ninguna conexión activa para cerrar.");
        }
    }

    @Override
    public List<Matricula> get() throws OperationNotSupportedException {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = """
        SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion,
               a.nombre AS nombreAlumno, a.telefono, a.correo, a.dni, a.fechaNacimiento
        FROM matricula m
        JOIN alumno a ON m.dni = a.dni
        ORDER BY m.fechaMatriculacion DESC, a.nombre ASC;
    """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Datos de la matrícula
                    int idMatricula = rs.getInt("idMatricula");
                    String cursoAcademico = rs.getString("cursoAcademico");
                    LocalDate fechaMatriculacion = rs.getDate("fechaMatriculacion").toLocalDate();
                    LocalDate fechaAnulacion = rs.getDate("fechaAnulacion") != null
                            ? rs.getDate("fechaAnulacion").toLocalDate()
                            : null;

                    // Datos del alumno
                    String nombreAlumno = rs.getString("nombreAlumno");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String dni = rs.getString("dni");
                    LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();

                    Alumno alumno = new Alumno(nombreAlumno, dni, correo, telefono, fechaNacimiento);

                    // Recuperar las asignaturas asociadas
                    List<Asignatura> asignaturas = getAsignaturasMatricula(idMatricula);

                    // Crear objeto Matricula
                    Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);
                    if (fechaAnulacion != null) {
                        matricula.setFechaAnulacion(fechaAnulacion);
                    }

                    matriculas.add(matricula);
                }
            }
        } catch (SQLException | OperationNotSupportedException e) {
            System.err.println("ERROR al obtener la lista de matrículas: " + e.getMessage());
        }

        return matriculas;
    }

    @Override
    public int getTamano() {
        int totalMatriculas = 0;
        String sql = "SELECT COUNT(*) AS total FROM matricula";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalMatriculas = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("ERROR: No se pudo obtener el número de matrículas: " + e.getMessage());
        }

        return totalMatriculas;
    }

    public List<Asignatura> getAsignaturasMatricula(int idMatricula) {
        List<Asignatura> asignaturas = new ArrayList<>();

        String sql = """
    SELECT 
        a.codigo, a.nombre, a.horasAnuales, a.curso, a.horasDesdoble, a.especialidadProfesorado,
        cf.codigo AS cfCodigo, cf.familiaProfesional, cf.nombre AS cfNombre, cf.horas,
        cf.grado, cf.nombreGrado, cf.numAniosGrado, cf.modalidad, cf.numEdiciones
    FROM asignaturasMatricula am
    JOIN asignatura a ON am.codigo = a.codigo
    JOIN cicloFormativo cf ON a.codigoCicloFormativo = cf.codigo
    WHERE am.idMatricula = ?
    """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idMatricula);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Construir objeto Grado
                    String gradoTipo = rs.getString("grado");
                    String nombreGrado = rs.getString("nombreGrado");
                    int numAniosGrado = rs.getInt("numAniosGrado");
                    String modalidadStr = rs.getString("modalidad");
                    int numEdiciones = rs.getInt("numEdiciones");

                    Grado grado;
                    if ("gradod".equalsIgnoreCase(gradoTipo)) {
                        Modalidad modalidad = Modalidad.valueOf(modalidadStr.toUpperCase());
                        grado = new GradoD(nombreGrado, numAniosGrado, modalidad);
                    } else if ("gradoe".equalsIgnoreCase(gradoTipo)) {
                        grado = new GradoE(nombreGrado, numAniosGrado, numEdiciones);
                    } else {
                        grado = new Grado(nombreGrado);
                        grado.setNumAnios(numAniosGrado);
                    }

                    // Construir CicloFormativo
                    CicloFormativo cicloFormativo = new CicloFormativo(
                            rs.getInt("cfCodigo"),
                            rs.getString("familiaProfesional"),
                            grado,
                            rs.getString("cfNombre"),
                            rs.getInt("horas")
                    );

                    // Convertir los campos de enums desde String
                    EspecialidadProfesorado especialidad = EspecialidadProfesorado.valueOf(rs.getString("especialidadProfesorado").toUpperCase());
                    Curso curso = Curso.valueOf(rs.getString("curso").toUpperCase());

                    // Construimos el objeto Asignatura
                    Asignatura asignatura = new Asignatura(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getInt("horasAnuales"),
                            curso,
                            rs.getInt("horasDesdoble"),
                            especialidad,
                            cicloFormativo
                    );

                    asignaturas.add(asignatura);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener asignaturas de la matrícula: " + e.getMessage());

        }

        return asignaturas;
    }

    private void insertarAsignaturasMatricula(int idMatricula, List<Asignatura> asignaturas) {

        String sql = "INSERT INTO asignaturasMatricula (idMatricula, codigo) VALUES (?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (Asignatura asignatura : asignaturas) {
                ps.setInt(1, idMatricula);
                ps.setString(2, asignatura.getCodigo()); // El campo correcto es 'codigo'
                ps.executeUpdate();
            }
            System.out.println("Asignaturas insertadas correctamente en la matrícula.");
        } catch (SQLException e) {
            System.err.println("ERROR: Al insertar asignaturas en la matrícula: " + e.getMessage());
        }
    }



    @Override
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        String sql = "INSERT INTO matricula (idMatricula, cursoAcademico, fechaMatriculacion, dni) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, matricula.getIdMatricula());
            ps.setString(2, matricula.getCursoAcademico()); // char(9)
            ps.setDate(3, Date.valueOf(matricula.getFechaMatriculacion())); // Date SQL
            ps.setString(4, matricula.getAlumno().getDni()); // char(9)

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new OperationNotSupportedException("ERROR: No se pudo insertar la matrícula.");
            } else {
                System.out.println("Matrícula insertada correctamente en la base de datos.");
                insertarAsignaturasMatricula(matricula.getIdMatricula(), matricula.getColeccionAsignaturas());

            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062 || (e.getMessage() != null && e.getMessage().contains("Duplicate entry"))) {
                throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
            }
        }

    }

    @Override
    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
        }

        String sql = """
        SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion,
               a.nombre AS nombreAlumno, a.telefono, a.correo, a.dni, a.fechaNacimiento
        FROM matricula m
        JOIN alumno a ON m.dni = a.dni
        WHERE m.idMatricula = ?;
    """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, matricula.getIdMatricula());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Datos de la matrícula
                    int idMatricula = rs.getInt("idMatricula");
                    String cursoAcademico = rs.getString("cursoAcademico");
                    LocalDate fechaMatriculacion = rs.getDate("fechaMatriculacion").toLocalDate();
                    LocalDate fechaAnulacion = rs.getDate("fechaAnulacion") != null
                            ? rs.getDate("fechaAnulacion").toLocalDate()
                            : null;

                    // Datos del alumno
                    String nombreAlumno = rs.getString("nombreAlumno");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String dni = rs.getString("dni");
                    LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();

                    Alumno alumno = new Alumno(nombreAlumno, dni, correo, telefono, fechaNacimiento);

                    // Recuperar las asignaturas asociadas
                    List<Asignatura> asignaturas = getAsignaturasMatricula(idMatricula);

                    // Crear objeto Matricula
                    Matricula matriculaResultante = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);
                    if (fechaAnulacion != null) {
                        matriculaResultante.setFechaAnulacion(fechaAnulacion);
                    }

                    return matriculaResultante;
                } else {
                    System.out.println("ERROR: No se encontró ninguna matrícula con el identificador introducido.");
                    return null;
                }
            }
        } catch (SQLException | OperationNotSupportedException e) {
            System.err.println("ERROR: Al buscar matrícula: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula==null) {
            throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
        }

        String sql = "DELETE FROM matricula WHERE idMatricula = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, matricula.getIdMatricula());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new OperationNotSupportedException("ERROR: No se encontró la matrícula con el identificador proporcionado.");
            } else {
                System.out.println("Matrícula eliminada correctamente.");
            }
        } catch (SQLException e) {
            throw new OperationNotSupportedException("ERROR: No se pudo eliminar la matrícula. " + e.getMessage());
        }
    }

    @Override
    public List<Matricula> get(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("ERROR: El alumno pasado como parámetro no puede ser nulo.");
        }

        List<Matricula> matriculas = new ArrayList<>();
        String sql = """
        SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion
        FROM matricula m
        WHERE m.dni = ?;
    """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, alumno.getDni()); // Usamos el DNI del alumno como criterio de búsqueda

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Datos de la matrícula
                    int idMatricula = rs.getInt("idMatricula");
                    String cursoAcademico = rs.getString("cursoAcademico");
                    LocalDate fechaMatriculacion = rs.getDate("fechaMatriculacion").toLocalDate();
                    LocalDate fechaAnulacion = rs.getDate("fechaAnulacion") != null
                            ? rs.getDate("fechaAnulacion").toLocalDate()
                            : null;

                    // Recuperar las asignaturas asociadas
                    List<Asignatura> asignaturas = getAsignaturasMatricula(idMatricula);

                    // Crear objeto Matricula
                    Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);
                    if (fechaAnulacion != null) {
                        matricula.setFechaAnulacion(fechaAnulacion);
                    }

                    matriculas.add(matricula);
                }
            }
        } catch (SQLException | OperationNotSupportedException e) {
            System.err.println("ERROR: Al obtener las matrículas del alumno: " + e.getMessage());
        }

        return matriculas;
    }

    @Override
    public List<Matricula> get(String cursoAcademico) {
        if (cursoAcademico == null) {
            throw new NullPointerException("ERROR: El curso académico proporcionado no puede ser nulo.");
        }

        List<Matricula> matriculas = new ArrayList<>();
        String sql = """
        SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion,
               a.nombre AS nombreAlumno, a.telefono, a.correo, a.dni, a.fechaNacimiento
        FROM matricula m
        JOIN alumno a ON m.dni = a.dni
        WHERE m.cursoAcademico = ?;
    """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cursoAcademico); // Usamos el curso académico como criterio de búsqueda

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Datos de la matrícula
                    int idMatricula = rs.getInt("idMatricula");
                    String cursoAcademicoDb = rs.getString("cursoAcademico");
                    LocalDate fechaMatriculacion = rs.getDate("fechaMatriculacion").toLocalDate();
                    LocalDate fechaAnulacion = rs.getDate("fechaAnulacion") != null
                            ? rs.getDate("fechaAnulacion").toLocalDate()
                            : null;

                    // Datos del alumno
                    String nombreAlumno = rs.getString("nombreAlumno");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String dni = rs.getString("dni");
                    LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();

                    Alumno alumno = new Alumno(nombreAlumno, dni, correo, telefono, fechaNacimiento);

                    // Recuperar las asignaturas asociadas
                    List<Asignatura> asignaturas = getAsignaturasMatricula(idMatricula);

                    // Crear objeto Matricula
                    Matricula matricula = new Matricula(idMatricula, cursoAcademicoDb, fechaMatriculacion, alumno, asignaturas);
                    if (fechaAnulacion != null) {
                        matricula.setFechaAnulacion(fechaAnulacion);
                    }

                    matriculas.add(matricula);
                }
            }
        } catch (SQLException | OperationNotSupportedException e) {
            System.err.println("ERROR: Al obtener las matrículas del curso académico: " + e.getMessage());
        }

        return matriculas;
    }

    @Override
    public List<Matricula> get(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("ERROR: El ciclo formativo proporcionado no puede ser nulo.");
        }

        List<Matricula> matriculas = new ArrayList<>();
        String sql = """
        SELECT m.idMatricula, m.cursoAcademico, m.fechaMatriculacion, m.fechaAnulacion,
               a.nombre AS nombreAlumno, a.telefono, a.correo, a.dni, a.fechaNacimiento
        FROM matricula m
        JOIN alumno a ON m.dni = a.dni
        JOIN asignaturasMatricula am ON m.idMatricula = am.idMatricula
        JOIN asignatura asig ON am.codigo = asig.codigo
        WHERE asig.codigoCicloFormativo = ?;
    """;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, cicloFormativo.getCodigo()); // Usamos el código del ciclo formativo como criterio de búsqueda

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Datos de la matrícula
                    int idMatricula = rs.getInt("idMatricula");
                    String cursoAcademico = rs.getString("cursoAcademico");
                    LocalDate fechaMatriculacion = rs.getDate("fechaMatriculacion").toLocalDate();
                    LocalDate fechaAnulacion = rs.getDate("fechaAnulacion") != null
                            ? rs.getDate("fechaAnulacion").toLocalDate()
                            : null;

                    // Datos del alumno
                    String nombreAlumno = rs.getString("nombreAlumno");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String dni = rs.getString("dni");
                    LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();

                    Alumno alumno = new Alumno(nombreAlumno, dni, correo, telefono, fechaNacimiento);

                    // Recuperamos las asignaturas asociadas
                    List<Asignatura> asignaturas = getAsignaturasMatricula(idMatricula);

                    // Creamos el objeto Matricula
                    Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturas);
                    if (fechaAnulacion != null) {
                        matricula.setFechaAnulacion(fechaAnulacion);
                    }

                    matriculas.add(matricula);
                }
            }
        } catch (SQLException | OperationNotSupportedException e) {
            System.err.println("ERROR: Al obtener las matrículas del ciclo formativo: " + e.getMessage());
        }

        return matriculas;
    }
}
