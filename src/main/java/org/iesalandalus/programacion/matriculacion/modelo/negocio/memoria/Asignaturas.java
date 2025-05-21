package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Asignaturas {

    private static List<Asignatura> coleccionAsignaturas = new ArrayList<>();


    public Asignaturas() {




    }


    private List<Asignatura> copiaProfundaAsignaturas (){

        List <Asignatura> copiaAsignaturas = new ArrayList<>();

        for (Asignatura asignatura : coleccionAsignaturas){
            copiaAsignaturas.add(new Asignatura(asignatura));
        }

        return copiaAsignaturas;
    }

    public List<Asignatura> get (){

        List<Asignatura> copiaAs=copiaProfundaAsignaturas();

        if (copiaAs.isEmpty()){
            throw new IllegalArgumentException("ERROR:No hay asignaturas matriculadas, inserte una.");
        }


        return copiaAs;
    }

    public void insertar (Asignatura asignatura)throws OperationNotSupportedException{

        if (!coleccionAsignaturas.contains(asignatura)){
            coleccionAsignaturas.add(asignatura);
        }else
            System.out.println("ERROR:La asignatura ya existe.");


    }

    public Asignatura buscar (Asignatura asignatura){

        Iterator <Asignatura> asignaturaIterator=coleccionAsignaturas.iterator();

        while (asignaturaIterator.hasNext()){
            Asignatura asignatura1= asignaturaIterator.next();

            if (asignatura1.equals(asignatura)){
                return asignatura1;
            }
        }
        return null;
    }


    public void borrar (Asignatura asignatura)throws OperationNotSupportedException{

        if (asignatura==null){
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }


        if (coleccionAsignaturas.contains(asignatura)){
            coleccionAsignaturas.remove(asignatura);
        }else{
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");

        }

    }


}
