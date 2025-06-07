package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import javax.swing.plaf.PanelUI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Matriculas implements IMatriculas {
    private Connection conexion;


    public Matriculas(){
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
    public List<Matricula> get() throws OperationNotSupportedException {
        return List.of();
    }

    @Override
    public int getTamano() {
        return 0;
    }

    public List<Asignatura> getAsignaturasMatricula(int idMatricula) {
        List<Asignatura> asignaturas = new ArrayList<>();

        String sql = """
        SELECT 
            a.codigo, a.nombre, a.horasAnuales, a.curso, a.horasDesdoble, a.especialidadProfesorado,
            cf.codigo AS cfCodigo, cf.familiaProfesional, cf.nombre AS cfNombre, cf.horas,
            cf.gradoTipo,  -- tipo de grado (GradoD o GradoE)
            g.nombre AS gradoNombre, g.numAnios, g.modalidad, g.numEdiciones
        FROM asignaturasMatricula am
        JOIN asignatura a ON am.codigo = a.codigo
        JOIN cicloFormativo cf ON a.codigoCicloFormativo = cf.codigo
        JOIN grado g ON cf.nombreGrado = g.nombre
        WHERE am.idMatricula = ?
    """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idMatricula);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Construir objeto Grado
                    String gradoTipo = rs.getString("gradoTipo");
                    Grado grado;

                    if ("GradoD".equalsIgnoreCase(gradoTipo)) {
                        Modalidad modalidad = Modalidad.valueOf(rs.getString("modalidad"));
                        grado = new GradoD(
                                rs.getString("gradoNombre"),
                                rs.getInt("numAnios"),
                                modalidad
                        );
                    } else if ("GradoE".equalsIgnoreCase(gradoTipo)) {
                        grado = new GradoE(
                                rs.getString("gradoNombre"),
                                rs.getInt("numAnios"),
                                rs.getInt("numEdiciones")
                        );
                    } else {
                        grado = new Grado(rs.getString("gradoNombre"));
                        grado.setNumAnios(rs.getInt("numAnios"));
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
                    EspecialidadProfesorado especialidad = EspecialidadProfesorado.valueOf(rs.getString("especialidadProfesorado"));
                    Curso curso = Curso.valueOf(rs.getString("curso"));

                    // Construir Asignatura
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
        if (conexion == null) {
            System.err.println("❌ No hay conexión a la base de datos.");
            return;
        }

        String sql = "INSERT INTO asignaturasMatricula (idMatricula, idAsignatura) VALUES (?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (Asignatura asignatura : asignaturas) {
                ps.setInt(1, idMatricula);
                ps.setString(2, asignatura.getCodigo()); // ← Ahora usamos setString porque el código es String
                ps.executeUpdate();
            }
            System.out.println("✅ Asignaturas insertadas correctamente en la matrícula.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar asignaturas en la matrícula: " + e.getMessage());
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
                System.out.println("✅ Matrícula insertada correctamente en la base de datos.");
            }

        } catch (SQLException e) {
            throw new OperationNotSupportedException("ERROR: No se pudo insertar la matrícula. " + e.getMessage());
        }

    }

    @Override
    public Matricula buscar(Matricula matricula) {
        return null;
    }

    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {

    }

    @Override
    public List<Matricula> get(Alumno alumno) {
        return List.of();
    }

    @Override
    public List<Matricula> get(String cursoAcademico) {
        return List.of();
    }

    @Override
    public List<Matricula> get(CicloFormativo cicloFormativo) {
        return List.of();
    }
}
