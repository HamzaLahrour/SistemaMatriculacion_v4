package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Alumnos {


    //Alumno [] coleccionAlumnos;

    private static List <Alumno> coleccionAlumnos = new ArrayList<>();

    public Alumnos(int capacidad) {

        if (capacidad<=0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

    }





    /*
    public int getTamano() {
        return tamano;
    }

     */



    private Alumno[] copiaProfundaAlumnos(){

        List <Alumno> copiaAlumno = new ArrayList<>();

        for (Alumno alumno : coleccionAlumnos){
            copiaAlumno.add(new Alumno(alumno));
        }


        return copiaAlumno.toArray(copiaAlumno.toArray(new Alumno[0]));
    }

    public Alumno[] get (){

        Alumno [] copia = copiaProfundaAlumnos();

        if (copia.length==0){
            throw new IllegalArgumentException("ERROR: No hay alumnos matriculados.");
        }

        return copia;
    }

    public void insertar (Alumno alumno){

        if (!coleccionAlumnos.contains(alumno)){
            coleccionAlumnos.add(alumno);
        }else {
            System.out.println("ERROR: El alumno introducido ya existe.");
        }

    }

    public Alumno buscar (Alumno alumno){
        Iterator <Alumno> alumnoIterator=coleccionAlumnos.iterator();

        while (alumnoIterator.hasNext()){
            Alumno alumno1 = alumnoIterator.next();

            if (alumno1.equals(alumno)){
                return alumno1;
            }

        }

        return null;
    }

    public void borrar(Alumno alumno)throws OperationNotSupportedException{

        if (alumno==null){
            throw new NullPointerException("ERROR:El alumno no puede ser nulo.");
        }

        if (coleccionAlumnos.contains(alumno)){
            coleccionAlumnos.remove(alumno);
        }else
            throw new OperationNotSupportedException("ERROR:El alumno no se encuentra en la coleccion.");
    }







}
