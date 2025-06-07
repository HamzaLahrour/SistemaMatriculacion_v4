package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Asignaturas implements IAsignaturas {
    private Connection conexion;
    private static Asignaturas instancia=null;

    private static Asignaturas getInstancia() {
        if (instancia == null) {
            instancia = new Asignaturas();
        }
        return instancia;
    }

    public Asignaturas(){
        comenzar();
    }

    @Override
    public void comenzar() {
        MySQL gestorBD= new MySQL();
        this.conexion= gestorBD.establecerConexion();
        if (this.conexion != null) {
            System.out.println("✅ Conexión con la base de datos iniciada en Alumnos.");
        } else {
            System.err.println("❌ No se pudo establecer la conexión en Alumnos.");
        }
    }

    @Override
    public void terminar() {

    }

    @Override
    public List<Asignatura> get() {
        List<Asignatura> listaAsignaturas = new ArrayList<>();
        String sql = "SELECT a.codigo, a.nombre, a.horasAnuales, a.curso, a.horasDesdoble, a.especialidadProfesorado, " +
                "c.codigo AS cicloCodigo, c.familiaProfesional, c.gradoNombre, c.gradoNumAnios, c.gradoTipo, c.nombre AS cicloNombre, c.horas " +
                "FROM asignatura a " +
                "JOIN cicloFormativo c ON a.codigoCicloFormativo = c.codigo " +
                "ORDER BY a.nombre";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Datos asignatura
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                int horasAnuales = rs.getInt("horasAnuales");
                String cursoStr = rs.getString("curso");
                int horasDesdoble = rs.getInt("horasDesdoble");
                String especialidadStr = rs.getString("especialidadProfesorado");

                // Datos ciclo formativo
                int codigoCiclo = rs.getInt("cicloCodigo");
                String familiaProfesional = rs.getString("familiaProfesional");
                String gradoNombre = rs.getString("gradoNombre");
                int gradoNumAnios = rs.getInt("gradoNumAnios");
                String gradoTipo = rs.getString("gradoTipo");
                String cicloNombre = rs.getString("cicloNombre");
                int horasCiclo = rs.getInt("horas");

                // Convertir cursoStr a enum Curso
                Curso curso = null;
                if ("primero".equalsIgnoreCase(cursoStr)) {
                    curso = Curso.PRIMERO;
                } else if ("segundo".equalsIgnoreCase(cursoStr)) {
                    curso = Curso.SEGUNDO;
                } else {
                    throw new SQLException("Curso desconocido: " + cursoStr);
                }

                // Convertir especialidadStr a enum EspecialidadProfesorado
                EspecialidadProfesorado especialidad = null;
                if ("informatica".equalsIgnoreCase(especialidadStr)) {
                    especialidad = EspecialidadProfesorado.INFORMATICA;
                } else if ("sistemas".equalsIgnoreCase(especialidadStr)) {
                    especialidad = EspecialidadProfesorado.SISTEMAS;
                } else if ("fol".equalsIgnoreCase(especialidadStr)) {
                    especialidad = EspecialidadProfesorado.FOL;
                } else {
                    throw new SQLException("Especialidad desconocida: " + especialidadStr);
                }

                // Construir objeto Grado según gradoTipo (ajusta si tienes más info)
                Grado grado = null;
                if ("GradoD".equalsIgnoreCase(gradoTipo)) {
                    // Por ejemplo, si tienes modalidad en BD puedes leerla aquí
                    // Suponemos modalidad por defecto para ejemplo
                    grado = new GradoD(gradoNombre, gradoNumAnios, Modalidad.PRESENCIAL);
                } else if ("GradoE".equalsIgnoreCase(gradoTipo)) {
                    grado = new GradoE(gradoNombre, gradoNumAnios, 1); // Ajusta el número de ediciones según tu BD
                } else {
                    throw new SQLException("Tipo de grado desconocido: " + gradoTipo);
                }

                // Crear ciclo formativo completo
                CicloFormativo cicloFormativo = new CicloFormativo(codigoCiclo, familiaProfesional, grado, cicloNombre, horasCiclo);

                // Crear asignatura con todos sus datos
                Asignatura asignatura = new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidad, cicloFormativo);

                listaAsignaturas.add(asignatura);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener asignaturas: " + e.getMessage());
            e.printStackTrace();
        }

        return listaAsignaturas;
    }

    @Override
    public int getTamano() {
        return 0;
    }

    @Override
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        String sql = "INSERT INTO asignatura (codigo, nombre, horasAnuales, curso, horasDesdoble, especialidadProfesorado, codigoCicloFormativo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, asignatura.getCodigo());
            stmt.setString(2, asignatura.getNombre());
            stmt.setInt(3, asignatura.getHorasAnuales());
            stmt.setString(4, asignatura.getCurso().name());  // Asumiendo que Curso es enum
            stmt.setInt(5, asignatura.getHorasDesdoble());
            stmt.setString(6, asignatura.getEspecialidadProfesorado().name());  // Asumiendo que EspecialidadProfesorado es enum
            stmt.setInt(7, asignatura.getCicloFormativo().getCodigo());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Asignatura insertada correctamente.");
            } else {
                System.out.println("No se pudo insertar la asignatura.");
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar asignatura: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede buscar una asignatura nula.");
        }

        Asignatura resultado = null;

        String sql = "SELECT a.codigo, a.nombre, a.horasAnuales, a.curso, a.horasDesdoble, a.especialidadProfesorado, " +
                "c.codigo AS codigoCiclo, c.familiaProfesional, c.grado, c.nombre AS nombreCiclo, c.horas, " +
                "c.numAnios, c.modalidad, c.numEdiciones " +
                "FROM asignatura a " +
                "INNER JOIN cicloFormativo c ON a.codigoCicloFormativo = c.codigo " +
                "WHERE a.codigo = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, asignatura.getCodigo());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Grado grado;
                    String gradoBD = rs.getString("grado");
                    String nombreGrado = rs.getString("nombreCiclo");
                    int numAnios = rs.getInt("numAnios");

                    if ("gradod".equalsIgnoreCase(gradoBD)) {
                        String modalidadStr = rs.getString("modalidad");
                        Modalidad modalidad = Modalidad.valueOf(modalidadStr); // Ajusta si Modalidad es enum o clase
                        grado = new GradoD(nombreGrado, numAnios, modalidad);

                    } else if ("gradoe".equalsIgnoreCase(gradoBD)) {
                        int numEdiciones = rs.getInt("numEdiciones");
                        grado = new GradoE(nombreGrado, numAnios, numEdiciones);

                    } else {
                        throw new SQLException("Valor de grado no reconocido: " + gradoBD);
                    }

                    CicloFormativo cicloFormativo = new CicloFormativo(
                            rs.getInt("codigoCiclo"),
                            rs.getString("familiaProfesional"),
                            grado,
                            nombreGrado,
                            rs.getInt("horas")
                    );

                    resultado = new Asignatura(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getInt("horasAnuales"),
                            Curso.valueOf(rs.getString("curso")),  // si es enum
                            rs.getInt("horasDesdoble"),
                            EspecialidadProfesorado.valueOf(rs.getString("especialidadProfesorado")), // si es enum
                            cicloFormativo
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar asignatura: " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }

        String sql = "DELETE FROM asignatura WHERE codigo = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, asignatura.getCodigo());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura con ese código.");
            } else {
                System.out.println("Asignatura borrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al borrar asignatura: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public EspecialidadProfesorado getEspecialidadProfesoradoDesdeBD(String codigoAsignatura) throws SQLException {
        String sql = "SELECT especialidadProfesorado FROM asignatura WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigoAsignatura);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String espStr = rs.getString("especialidadProfesorado");
                    if (espStr == null) {
                        throw new SQLException("La asignatura no tiene especialidad asignada.");
                    }
                    switch (espStr.toLowerCase()) {
                        case "informatica":
                            return EspecialidadProfesorado.INFORMATICA;
                        case "sistemas":
                            return EspecialidadProfesorado.SISTEMAS;
                        case "fol":
                            return EspecialidadProfesorado.FOL;
                        default:
                            throw new SQLException("Especialidad desconocida en base de datos: " + espStr);
                    }
                } else {
                    throw new SQLException("No existe asignatura con código: " + codigoAsignatura);
                }
            }
        }
    }

    public Curso getCursoDesdeBD(String codigoAsignatura) throws SQLException {
        Curso curso = null;
        String sql = "SELECT curso FROM asignatura WHERE codigo = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, codigoAsignatura);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String cursoStr = rs.getString("curso"); // "primero" o "segundo"
                    switch (cursoStr.toLowerCase()) {
                        case "primero":
                            curso = Curso.PRIMERO;
                            break;
                        case "segundo":
                            curso = Curso.SEGUNDO;
                            break;
                        default:
                            throw new SQLException("Valor desconocido para curso en BD: " + cursoStr);
                    }
                } else {
                    throw new SQLException("No existe asignatura con código: " + codigoAsignatura);
                }
            }
        }
        return curso;
    }


}
