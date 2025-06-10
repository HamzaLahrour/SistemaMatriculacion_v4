package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Alumnos implements IAlumnos {

    private Connection conexion;
    private static Alumnos instancia=null;

    private static Alumnos getInstancia() {
        if (instancia == null) {
            instancia = new Alumnos();
        }
        return instancia;
    }

    public Alumnos(){
        comenzar();
    }


    @Override
    public void comenzar() {
        MySQL gestorBD= new MySQL();
        this.conexion= gestorBD.establecerConexion();
        if (this.conexion != null) {
            System.out.println("Conexión con la base de datos iniciada en Alumnos.");
        } else {
            System.err.println("ERROR: No se pudo establecer la conexión en Alumnos.");
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

    @Override
    public List<Alumno> get() {
        List<Alumno> listaAlumnos = new ArrayList<>();
        String sql = "SELECT nombre, telefono, correo, dni, fechaNacimiento FROM alumno ORDER BY dni";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");
                String dni = rs.getString("dni");
                LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();

                Alumno alumno = new Alumno(nombre, dni, correo, telefono, fechaNacimiento);
                listaAlumnos.add(alumno);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener alumnos: " + e.getMessage());
            e.printStackTrace();
        }

        return listaAlumnos;
    }

    @Override
    public int getTamano() {
        int cantidad = 0;
        String sql = "SELECT COUNT(*) AS total FROM alumno";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                cantidad = rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el tamaño: " + e.getMessage());
            e.printStackTrace();
        }

        return cantidad;
    }

    @Override
    public void insertar(Alumno alumno) {
        String sql = "INSERT INTO alumno (nombre, telefono, correo, dni, fechaNacimiento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getTelefono());
            stmt.setString(3, alumno.getCorreo());
            stmt.setString(4, alumno.getDni());
            stmt.setDate(5, java.sql.Date.valueOf(alumno.getFechaNacimiento())); // convierte LocalDate → java.sql.Date

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Alumno insertado correctamente.");
            } else {
                System.out.println("No se pudo insertar el alumno.");
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar alumno: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Alumno buscar(Alumno alumno) {
        String sql = "SELECT nombre, telefono, correo, dni, fechaNacimiento FROM alumno WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Ponemos el DNI que queremos buscar
            stmt.setString(1, alumno.getDni());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Creamos un nuevo Alumno con todos los datos desde la BD
                    String nombre = rs.getString("nombre");
                    String telefono = rs.getString("telefono");
                    String correo = rs.getString("correo");
                    String dni = rs.getString("dni");
                    LocalDate fechaNacimiento = rs.getDate("fechaNacimiento").toLocalDate();

                    // Creamos el objeto Alumno usando tu constructor completo
                    return new Alumno(nombre, dni, correo, telefono, fechaNacimiento);
                } else {
                    // No hay alumno con ese DNI
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar alumno: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        String sql = "DELETE FROM alumno WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, alumno.getDni());
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new OperationNotSupportedException("ERROR: No existe el alumno a borrar.");
            } else {
                System.out.println("Alumno borrado correctamente.");
            }
        } catch (SQLException e) {
            // Podrías lanzar otra excepción o convertirla
            throw new OperationNotSupportedException("Error al borrar alumno: " + e.getMessage());
        }
    }
}
