package org.iesalandalus.programacion.matriculacion;


import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
//import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

import javax.naming.OperationNotSupportedException;

import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos.getGrado;


public class MainApp {


    public static void main(String[] args) throws OperationNotSupportedException {



        System.out.println("Iniciando la aplicacion...");
        Modelo modelo = procesarArgumentosFuenteDatos(args);

        Vista vista = new Vista();

        Controlador controlador = new Controlador(modelo, vista);

        controlador.comenzar();

        System.out.println("Gracias por usar la aplicacion!");





    }

    private static Modelo procesarArgumentosFuenteDatos(String[] args) {
        if (args.length == 0) {
            // Si no hay argumentos, usar la fuente de datos por defecto (MEMORIA)
            System.out.println("No se especificó ninguna fuente de datos. Usando la fuente por defecto: MEMORIA");
            return new Modelo(FactoriaFuenteDatos.MEMORIA);
        }

        switch (args[0]) {
            case "-fdmemoria":
                System.out.println("Usando la fuente de datos: MEMORIA");
                return new Modelo(FactoriaFuenteDatos.MEMORIA);

            case "-fdmysql":
                System.out.println("Usando la fuente de datos: MYSQL");
                return new Modelo(FactoriaFuenteDatos.MYSQL);

            default:
                throw new IllegalArgumentException("ERROR: Fuente de datos no reconocida. Usa -fdmemoria o -fdmysql.");
        }

    }
}



