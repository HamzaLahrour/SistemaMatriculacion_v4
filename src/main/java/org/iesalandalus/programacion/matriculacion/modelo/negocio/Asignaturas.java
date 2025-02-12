package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;

public class Asignaturas {

    int capacidad;
    int tamano;
    Asignatura[] coleccionAsignaturas;

    public Asignaturas(int capacidad) {

        if (capacidad<=0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad = capacidad;
        this.coleccionAsignaturas=new Asignatura[capacidad];
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getTamano() {
        return tamano;
    }

    private Asignatura[] copiaProfundaAsignaturas (){

        Asignatura[] copiaProfundaAsignatura= new Asignatura[tamano];

        for (int i=0;i<tamano;i++){

            copiaProfundaAsignatura[i]=new Asignatura(coleccionAsignaturas[i]);

        }
        return copiaProfundaAsignatura;

    }

    public Asignatura[] get (){

        if (copiaProfundaAsignaturas().length==0){
            throw new IllegalArgumentException("ERROR: No se encuentran asignaturas matriculadas, introduzca la opcion 9 para insertar asignaturas.");
        }

        return copiaProfundaAsignaturas();
    }

    public void insertar (Asignatura asignatura)throws OperationNotSupportedException{

        if (asignatura==null){
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }

        for (int i=0;i<tamano;i++){
            if (coleccionAsignaturas[i].equals(asignatura)){
                throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
            }
        }

        if (tamano==capacidad){
            throw new OperationNotSupportedException("ERROR: No se aceptan más asignaturas.");
        }

        if (tamano<capacidad){
            coleccionAsignaturas[tamano]=new Asignatura(asignatura);
            tamano++;
        }



    }

    public Asignatura buscar (Asignatura asignatura){

        for (int i=0;i<tamano;i++){

            if (coleccionAsignaturas[i].equals(asignatura)){
                return coleccionAsignaturas[i];
            }
        }
        return null;
    }

    public void borrar (Asignatura asignatura)throws OperationNotSupportedException{

        if (asignatura==null){
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }


        for (int i=0;i<tamano;i++){
            if (coleccionAsignaturas[i].equals(asignatura)){
                for (int r=i;r<tamano-1;r++){
                    coleccionAsignaturas[r]=coleccionAsignaturas[r+1];
                }
                tamano--;

                return;
            }
        }

        throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
    }


}
