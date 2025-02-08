package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;

public class CiclosFormativos {

    int capacidad;
    int tamano;
    CicloFormativo [] coleccionCiclos;

    public CiclosFormativos(int capacidad) {

        if (capacidad<=0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad = capacidad;
        this.coleccionCiclos=new CicloFormativo[capacidad];
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getTamano() {
        return tamano;
    }

    private CicloFormativo[] copiaProfundaCiclosFormativos(){

        CicloFormativo[] copiaCicloFormativo=new CicloFormativo[tamano];

        for (int i=0;i<tamano;i++){

            copiaCicloFormativo[i]=new CicloFormativo(coleccionCiclos[i]);

        }
        return copiaCicloFormativo;

    }

    public CicloFormativo[] get (){

        if (copiaProfundaCiclosFormativos().length==0){
            throw new IllegalArgumentException("ERROR: No se encuentran ciclos formativos, inserte uno.");
        }

        return copiaProfundaCiclosFormativos();
    }

    public void insertar (CicloFormativo cicloFormativo)throws OperationNotSupportedException{

        if (cicloFormativo==null){
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }
        for (int i=0;i<tamano;i++){

          if (coleccionCiclos[i].equals(cicloFormativo)){

              throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");

          }

        }

        if (tamano==capacidad){

            throw new OperationNotSupportedException("ERROR: No se aceptan más ciclos formativos.");
        }

        if (tamano<capacidad){
            coleccionCiclos[tamano]=new CicloFormativo(cicloFormativo);
            tamano++;
        }

    }

    public CicloFormativo buscar (CicloFormativo cicloFormativo){
        for (int i=0;i<tamano;i++){

            if (coleccionCiclos[i].equals(cicloFormativo)){
                return coleccionCiclos[i];
            }
        }
        return null;

    }

    public void borrar (CicloFormativo cicloFormativo)throws OperationNotSupportedException{

        if (cicloFormativo==null){
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }

        for (int i=0;i<tamano;i++){

            if (coleccionCiclos[i].equals(cicloFormativo)){

                for (int r=i;r<tamano-1;r++){
                    coleccionCiclos[r]=coleccionCiclos[r+1];
                }
                    tamano--;

                    return;
            }

        }

        throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");
    }


}
