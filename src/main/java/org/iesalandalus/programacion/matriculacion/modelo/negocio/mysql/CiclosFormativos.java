package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiclosFormativos implements ICiclosFormativos {
    private Connection conexion;
    private static CiclosFormativos instancia = null;

    public CiclosFormativos(){
        comenzar();
    }

    private static CiclosFormativos getInstancia() {
        if (instancia == null) {
            instancia = new CiclosFormativos();
        }
        return instancia;
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
    public List<CicloFormativo> get() {
        List<CicloFormativo> lista = new ArrayList<>();
        if (conexion == null) {
            System.out.println("No hay conexión a la base de datos.");
            return lista;
        }

        String sql = "SELECT codigo, familiaProfesional, grado, nombre, horas, nombreGrado, numAniosGrado, modalidad, numEdiciones " +
                "FROM cicloFormativo ORDER BY nombre";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String familiaProfesional = rs.getString("familiaProfesional");
                String nombreCiclo = rs.getString("nombre");
                int horas = rs.getInt("horas");

                // Datos para crear el grado
                String gradoString = rs.getString("grado");
                String nombreGrado = rs.getString("nombreGrado");
                int numAniosGrado = rs.getInt("numAniosGrado");

                Grado grado = null;

                if ("gradod".equalsIgnoreCase(gradoString)) {
                    // Modalidad es enum, la lees como string y la conviertes
                    String modalidadStr = rs.getString("modalidad");
                    Modalidad modalidad = modalidadStr != null ? Modalidad.valueOf(modalidadStr.toUpperCase()) : null;
                    grado = new GradoD(nombreGrado, numAniosGrado, modalidad);
                } else if ("gradoe".equalsIgnoreCase(gradoString)) {
                    int numEdiciones = rs.getInt("numEdiciones");
                    grado = new GradoE(nombreGrado, numAniosGrado, numEdiciones);
                } else {
                    throw new SQLException("Valor inesperado en grado: " + gradoString);
                }

                CicloFormativo ciclo = new CicloFormativo(codigo, familiaProfesional, grado, nombreCiclo, horas);
                lista.add(ciclo);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los ciclos formativos: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public int getTamano() {
        if (conexion == null) {
            throw new IllegalStateException("No hay conexión a la base de datos.");
        }

        final String sql = "SELECT COUNT(*) FROM cicloFormativo";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al contar los ciclos formativos: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (conexion == null) {
            throw new IllegalStateException("No hay conexión a la base de datos.");
        }
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("No se puede insertar un ciclo formativo nulo.");
        }

        String sql = "INSERT INTO cicloFormativo(codigo, familiaProfesional, grado, nombre, horas, nombreGrado, numAniosGrado, modalidad, numEdiciones) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cicloFormativo.getCodigo());
            ps.setString(2, cicloFormativo.getFamiliaProfesional());

            // Para grado, asumimos que es un objeto de clase Grado (o sus derivadas)
            // y el atributo 'grado' en la tabla es ENUM('gradod','gradoe')
            // Por lo tanto hay que mapearlo a cadena:
            String tipoGrado = null;
            if (cicloFormativo.getGrado() instanceof GradoD) {
                tipoGrado = "gradod";
            } else if (cicloFormativo.getGrado() instanceof GradoE) {
                tipoGrado = "gradoe";
            } else {
                throw new IllegalArgumentException("El grado debe ser GradoD o GradoE");
            }
            ps.setString(3, tipoGrado);

            ps.setString(4, cicloFormativo.getNombre());
            ps.setInt(5, cicloFormativo.getHoras());

            // Campos relacionados con el grado:
            ps.setString(6, cicloFormativo.getGrado().getNombre());
            ps.setInt(7, cicloFormativo.getGrado().getNumAnios());

            if (cicloFormativo.getGrado() instanceof GradoD) {
                GradoD gradoD = (GradoD) cicloFormativo.getGrado();
                ps.setString(8, gradoD.getModalidad().toString().toLowerCase()); // modalidad enum('presencial','semipresencial')
                ps.setNull(9, Types.INTEGER);
            } else if (cicloFormativo.getGrado() instanceof GradoE) {
                GradoE gradoE = (GradoE) cicloFormativo.getGrado();
                ps.setNull(8, Types.VARCHAR);
                ps.setInt(9, gradoE.getNumEdiciones());
            } else {
                ps.setNull(8, Types.VARCHAR);
                ps.setNull(9, Types.INTEGER);
            }

            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new OperationNotSupportedException("Ya existe un ciclo formativo con ese código.");
        } catch (SQLException e) {
            throw new OperationNotSupportedException("Error al insertar el ciclo formativo: " + e.getMessage());
        }
    }

    @Override
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (conexion == null) {
            throw new IllegalStateException("No hay conexión a la base de datos.");
        }
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("No se puede buscar un ciclo formativo nulo.");
        }

        final String sql = "SELECT * FROM cicloFormativo WHERE codigo = ?";
        CicloFormativo encontrado = null;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cicloFormativo.getCodigo());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int codigo = rs.getInt("codigo");
                    String familiaProfesional = rs.getString("familiaProfesional");
                    String nombre = rs.getString("nombre");
                    int horas = rs.getInt("horas");
                    String nombreGrado = rs.getString("nombreGrado");
                    int numAniosGrado = rs.getInt("numAniosGrado");
                    String tipoGrado = rs.getString("grado");

                    Grado grado;

                    if ("gradod".equalsIgnoreCase(tipoGrado)) {
                        String modalidadStr = rs.getString("modalidad");
                        Modalidad modalidad = Modalidad.valueOf(modalidadStr.toUpperCase());
                        grado = new GradoD(nombreGrado, numAniosGrado, modalidad);
                    } else if ("gradoe".equalsIgnoreCase(tipoGrado)) {
                        int numEdiciones = rs.getInt("numEdiciones");
                        grado = new GradoE(nombreGrado, numAniosGrado, numEdiciones);
                    } else {
                        throw new IllegalArgumentException("Tipo de grado no reconocido: " + tipoGrado);
                    }

                    encontrado = new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar el ciclo formativo: " + e.getMessage());
        }

        return encontrado;
    }

    @Override
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (conexion == null) {
            throw new IllegalStateException("No hay conexión a la base de datos.");
        }
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("No se puede borrar un ciclo formativo nulo.");
        }

        final String sql = "DELETE FROM cicloFormativo WHERE codigo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cicloFormativo.getCodigo());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new OperationNotSupportedException("No existe ningún ciclo formativo con ese código.");
            }

        } catch (SQLException e) {
            throw new OperationNotSupportedException("Error al borrar el ciclo formativo: " + e.getMessage());
        }
    }
}
