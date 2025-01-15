package org.iesalandalus.programacion.matriculacion;


import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.vista.Consola;
import org.iesalandalus.programacion.matriculacion.vista.Opcion;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainApp {
    public static final int CAPACIDAD=3;





    public static void main(String[] args) throws OperationNotSupportedException {





        CicloFormativo ciclo1=new CicloFormativo(8888,"ZGA",Grado.GDCFGB,"daw",6);
        CicloFormativo ciclo2=new CicloFormativo(8886,"jra",Grado.GDCFGB,"daw",6);
        CicloFormativo ciclo3=new CicloFormativo(8884,"pañ",Grado.GDCFGB,"daw",6);

        CicloFormativo ciclo4=new CicloFormativo(8885,"Zaq",Grado.GDCFGB,"daw",6);





        Asignatura asignatura1 = new Asignatura("0102","progrmacion",12,Curso.PRIMERO,4,EspecialidadProfesorado.FOL,ciclo1);
        Asignatura asignatura2 = new Asignatura("0104","Entornos",12,Curso.PRIMERO,5,EspecialidadProfesorado.FOL,ciclo2);
        Asignatura asignatura3 = new Asignatura("0108","lenguaje",12,Curso.PRIMERO,5,EspecialidadProfesorado.FOL,ciclo3);
        Asignatura asignatura4 = new Asignatura("0101","FOL",12,Curso.PRIMERO,5,EspecialidadProfesorado.FOL,ciclo4);
        Asignatura asignatura5 = new Asignatura("0103","SSII",12,Curso.PRIMERO,5,EspecialidadProfesorado.FOL,ciclo4);


        Asignaturas asignaturas=new Asignaturas(5);

        try {
            asignaturas.insertar(asignatura1);
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
        asignaturas.insertar(asignatura2);
        asignaturas.insertar(asignatura3);
        asignaturas.insertar(asignatura4);




        for (Asignatura assa: asignaturas.get()){


        }


        Consola.mostrarMenu();
        Consola.leerAlumno();



        Alumno alumno=new Alumno("Hamza Lahrour Charif","78270122H","hamzalahrour2@gmail.com","672881917",LocalDate.of(2004,02,24));
        Matricula matricula=new Matricula(4521,"23-26",LocalDate.now(),alumno, asignaturas.get());









    }










    }




