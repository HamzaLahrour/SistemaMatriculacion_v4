package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.Matricula;

import javax.naming.OperationNotSupportedException;

public class Matriculas {

    private  int capacidad;
    private int tamano;

    private Matricula[] coleccionMatriculas;

    public Matriculas(int capacidad) {

        if (capacidad<=0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad = capacidad;
        this.coleccionMatriculas = new Matricula[capacidad];
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getTamano(){
        return tamano;
    }

    private Matricula[] copiaProfundaMatriculas()throws OperationNotSupportedException{

        Matricula[] copiaProfundaMatriculas=new Matricula[tamano];

        for (int i=0;i<tamano;i++){

            copiaProfundaMatriculas[i]=new Matricula(coleccionMatriculas[i]);

        }
        return copiaProfundaMatriculas;
    }

    public Matricula [] get ()throws OperationNotSupportedException{
        return copiaProfundaMatriculas();
    }

    public void insertar (Matricula matricula)throws OperationNotSupportedException {

        if (matricula==null){
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        for (int i=0;i<tamano;i++){

            if (coleccionMatriculas[i].equals(matricula)){

                throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
            }

        }

        if (tamano==capacidad){
            throw new OperationNotSupportedException("ERROR: No se aceptan más matrículas.");
        }

        if (tamano<capacidad){

            coleccionMatriculas[tamano]=new Matricula(matricula);
            tamano++;

        }
    }

    public Matricula buscar  (Matricula matricula){

        for (int i=0;i<tamano;i++){

            if (coleccionMatriculas[i].equals(matricula)){
                return coleccionMatriculas[i];
            }

        }
        return null;
    }

    public void borrar (Matricula matricula)throws OperationNotSupportedException{

        if (matricula==null){
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }

        for (int i=0;i<tamano;i++){
            if (coleccionMatriculas[i].equals(matricula)){
                for (int j=i;j<tamano-1;j++){
                    coleccionMatriculas[j]=coleccionMatriculas[j+1];
                }
                tamano--;

                return;
            }
        }
        throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
    }



}
