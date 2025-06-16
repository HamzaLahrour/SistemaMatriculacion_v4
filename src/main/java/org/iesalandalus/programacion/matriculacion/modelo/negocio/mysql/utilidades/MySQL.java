package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private static final String HOST="daw-25.codp6d0vjozl.us-east-1.rds.amazonaws.com";
    private static final String ESQUEMA="sistemamatriculacion";
    private static final String USUARIO="admin";
    private static final String CONTRASEÑA="Hamza1234.";
    Connection conexion;

    public MySQL (){

    }

    public Connection establecerConexion(){

        String url = "jdbc:mysql://" + HOST + ":3306/" + ESQUEMA;
        try {
            conexion = DriverManager.getConnection(url, USUARIO, CONTRASEÑA);
            conexion.setAutoCommit(true);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println(" Error al conectar: " + e.getMessage());
        }
        return conexion;
    }

    public void cerrarConexion() {
        if (conexion == null){
            return;
        }

        try {
            if (!conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        } finally {
            conexion = null; // Liberar referencia por seguridad
        }
    }
}


