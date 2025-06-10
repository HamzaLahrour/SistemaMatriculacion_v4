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

    public static CiclosFormativos getInstancia() {
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
            System.out.println("Conexión con la base de datos iniciada en CiclosFormativos.");
        } else {
            System.err.println("ERROR: No se pudo establecer la conexión en CiclosFormativos.");
        }
    }

    @Override
    public void terminar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión a la base de datos cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("ERROR: No se pudo cerrar la conexión a la base de datos: " + e.getMessage());
            }
        } else {
            System.out.println("ERROR: No hay ninguna conexión activa para cerrar.");
        }
    }

    public static Grado getGrado(String tipoGrado, String nombreGrado, int numAniosGrado, String modalidad, int numEdiciones) {

        if ("gradod".equalsIgnoreCase(tipoGrado)) {
            Modalidad mod = null;
            if (modalidad != null) {
                mod = Modalidad.valueOf(modalidad.toUpperCase());
            }
            return new GradoD(nombreGrado, numAniosGrado, mod);
        } else if ("gradoe".equalsIgnoreCase(tipoGrado)) {
            return new GradoE(nombreGrado, numAniosGrado, numEdiciones);
        } else {
            throw new IllegalArgumentException("Tipo de grado no reconocido: " + tipoGrado);
        }
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
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("No se puede insertar un ciclo formativo nulo.");
        }

        String sql = "INSERT INTO cicloFormativo (codigo, familiaProfesional, grado, nombre, horas, nombreGrado, numAniosGrado, modalidad, numEdiciones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cicloFormativo.getCodigo());
            ps.setString(2, cicloFormativo.getFamiliaProfesional());

            Grado grado = cicloFormativo.getGrado();

            if (grado instanceof GradoD gradoD) {
                ps.setString(3, "gradod");
                ps.setString(8, gradoD.getModalidad().name()); // modalidad
                ps.setNull(9, Types.INTEGER); // numEdiciones
            } else if (grado instanceof GradoE gradoE) {
                ps.setString(3, "gradoe");
                ps.setNull(8, Types.VARCHAR); // modalidad
                ps.setInt(9, gradoE.getNumEdiciones());
            } else {
                throw new OperationNotSupportedException("Tipo de grado no soportado.");
            }

            ps.setString(4, cicloFormativo.getNombre());
            ps.setInt(5, cicloFormativo.getHoras());
            ps.setString(6, grado.getNombre());
            ps.setInt(7, grado.getNumAnios());

            ps.executeUpdate();
            System.out.println("Ciclo formativo insertado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al insertar ciclo formativo: " + e.getMessage());
        }
    }

    @Override
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null) {
            throw new NullPointerException("No se puede buscar un ciclo formativo nulo.");
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
        if (cicloFormativo == null) {
            throw new NullPointerException("No se puede borrar un ciclo formativo nulo.");
        }

        final String sql = "DELETE FROM cicloFormativo WHERE codigo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, cicloFormativo.getCodigo());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo con ese código.");
            }

        } catch (SQLException e) {
            throw new OperationNotSupportedException("ERROR: Al borrar el ciclo formativo: " + e.getMessage());
        }
    }
}
