package org.iesalandalus.programacion.matriculacion;


import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
//import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;
import org.iesalandalus.programacion.matriculacion.vista.Consola;
import org.iesalandalus.programacion.matriculacion.vista.Opcion;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;


public class MainApp {
    public static final int CAPACIDAD=3;


    public static void main(String[] args) throws OperationNotSupportedException {


        System.out.println("Iniciando la aplicacion...");
        Modelo modelo = new Modelo();

        Vista vista = new Vista();

        Controlador controlador = new Controlador(modelo,vista);

        controlador.comenzar();

        System.out.println("Gracias por usar la aplicacion!");

    }










    }




