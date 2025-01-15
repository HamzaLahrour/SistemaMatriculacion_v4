package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {

    private Consola (){

    }

    public static void mostrarMenu (){

       Opcion [] opciones = Opcion.values();

        for (Opcion opcion : opciones){
            System.out.println(opcion);
        }

    }

    public static int elegirOpcion(){

        int opcion=0;

        System.out.println("Elige una opcion: ");
        opcion= Entrada.entero();

        return opcion;

    }

    public static Alumno leerAlumno(){

        String FORMATO_FECHA = "dd/MM/yyyy";



        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA);



        String nombre;
        String dni;
        String correo;
        String telefono;
        String fechaNacimiento;


        System.out.println("Introduzca el nombre del alumno: ");
        nombre=Entrada.cadena();
        System.out.println("Introduzca el dni del alumno: ");
        dni=Entrada.cadena();
        System.out.println("Introduzca el correo electronico del alumno: ");
        correo=Entrada.cadena();
        System.out.println("Introduzca el telefono del alumno: ");
        telefono=Entrada.cadena();


        System.out.println("Introduzca la fecha de nacimiento del alumno, debe ser en formato dd/MM/YYYY: ");
        fechaNacimiento=Entrada.cadena();

        LocalDate fechaNacimientO;

        try{
            fechaNacimientO= LocalDate.parse(fechaNacimiento,formatoFecha);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR: La fecha que has introducido no tiene el formato correcto.");
        }

        Alumno alumno = new Alumno(nombre,dni,correo,telefono,fechaNacimientO);


        return alumno;
    }


}
