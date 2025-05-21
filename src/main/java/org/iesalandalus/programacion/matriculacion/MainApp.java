package org.iesalandalus.programacion.matriculacion;


import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
//import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

import javax.naming.OperationNotSupportedException;


public class MainApp {



    public static void main(String[] args) throws OperationNotSupportedException {


        System.out.println("Iniciando la aplicacion...");
        Modelo modelo = new Modelo();

        Vista vista = new Vista();

        Controlador controlador = new Controlador(modelo,vista);

        controlador.comenzar();

        System.out.println("Gracias por usar la aplicacion!");

    }










    }




