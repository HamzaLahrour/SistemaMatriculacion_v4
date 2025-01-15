package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.Alumno;

import javax.naming.OperationNotSupportedException;
import java.util.Collection;
import java.util.Collections;

public class Alumnos {

    int capacidad;
    int tamano;
    Alumno [] coleccionAlumnos;

    public Alumnos(int capacidad) {

        if (capacidad<=0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad = capacidad;
        this.coleccionAlumnos=new Alumno[capacidad];
    }



    public int getCapacidad() {
        return capacidad;
    }

    public int getTamano() {
        return tamano;
    }



    private Alumno[] copiaProfundaAlumnos(){

        Alumno[] copiaAlumno=new Alumno[tamano];

        for (int i=0;i<tamano;i++){

            copiaAlumno[i]=new Alumno(coleccionAlumnos[i]);
        }
        return copiaAlumno;

    }

    public Alumno[] get (){

        return copiaProfundaAlumnos();
    }

    public void insertar (Alumno alumno)throws OperationNotSupportedException{

        if (alumno==null){

            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }

        if (tamano==capacidad){

            throw new OperationNotSupportedException("ERROR: No se aceptan más alumnos.");
        }

       for (int i=0;i<tamano;i++){
           if (coleccionAlumnos[i].getDni().equals(alumno.getDni())){
                throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
           }
       }

       if (tamano<capacidad){

            coleccionAlumnos[tamano]=new Alumno(alumno);
            tamano++;

        }

    }

    public Alumno buscar (Alumno alumno){
        for (int i=0;i<tamano;i++){
          if (coleccionAlumnos[i].equals(alumno)){
              return coleccionAlumnos[i];
          }
        }
            return null;
    }

    public void borrar(Alumno alumno)throws OperationNotSupportedException{

        if (alumno==null){
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }

        for (int i=0;i<tamano;i++){
            if (coleccionAlumnos[i].getDni().equals(alumno.getDni())){
                for (int r=i;r<tamano-1;r++){

                    coleccionAlumnos[r]=coleccionAlumnos[r+1];
                }
                tamano--;
                return;
            }
        }
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
    }

    private int buscarIndice(Alumno alumno){
        int indice=-1;

        for (int i=0;i<coleccionAlumnos.length;i++){
            if (coleccionAlumnos[i].equals(alumno)){
                indice=i;
            }
        }
        return indice;
    }




}
