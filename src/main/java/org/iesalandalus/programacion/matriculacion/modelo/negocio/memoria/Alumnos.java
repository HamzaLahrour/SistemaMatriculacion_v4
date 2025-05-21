package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Alumnos implements IAlumnos {



    private static List <Alumno> coleccionAlumnos = new ArrayList<>();

    public Alumnos() {

    }


    @Override
    public void comenzar(){
        System.out.println();
    }

    @Override
    public void terminar(){

    }


    private List<Alumno> copiaProfundaAlumnos(){

        List <Alumno> copiaAlumno = new ArrayList<>();

        for (Alumno alumno : coleccionAlumnos){
            copiaAlumno.add(new Alumno(alumno));
        }


        return copiaAlumno;
    }

    public List<Alumno> get (){

        List<Alumno> copia = copiaProfundaAlumnos();

        if (copia.isEmpty()){
            throw new IllegalArgumentException("ERROR: No hay alumnos matriculados.");
        }

        return copia;
    }


    @Override
    public void insertar (Alumno alumno){

        if (!coleccionAlumnos.contains(alumno)){
            coleccionAlumnos.add(alumno);
        }else {
            throw new IllegalArgumentException("ERROR:El alumno ya existe.");
        }

    }
    @Override
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
    @Override
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
