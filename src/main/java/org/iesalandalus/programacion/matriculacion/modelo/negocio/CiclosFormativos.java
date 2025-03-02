package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CiclosFormativos {

    List <CicloFormativo> coleccionCiclos = new ArrayList<>();

    public CiclosFormativos() {


    }

    private List<CicloFormativo> copiaProfundaCiclosFormativos(){

        List<CicloFormativo> copiaCiclo =new ArrayList<>();

        for (CicloFormativo cicloFormativo : coleccionCiclos){
            copiaCiclo.add(new CicloFormativo(cicloFormativo)); //Usamos el constructor copia
        }
        return copiaCiclo;
    }

    public List<CicloFormativo> get (){

       List<CicloFormativo> copiaCic = copiaProfundaCiclosFormativos();
        if (copiaCic.isEmpty()){
            throw new IllegalArgumentException("ERROR: No hay ciclos matriculados.");
        }

        return copiaCic;
    }

    public void insertar (CicloFormativo cicloFormativo)throws OperationNotSupportedException{

        if (!coleccionCiclos.contains(cicloFormativo)) {
        coleccionCiclos.add(cicloFormativo);
        }else {
            System.out.println("ERROR:El ciclo formativo ya existe.");
        }
    }

    public CicloFormativo buscar (CicloFormativo cicloFormativo){

        Iterator<CicloFormativo> cicloFormativoIterator = coleccionCiclos.iterator();

        while (cicloFormativoIterator.hasNext()){
            CicloFormativo cicloFormativo1 = cicloFormativoIterator.next();

            if (cicloFormativo1.equals(cicloFormativo)){
                return cicloFormativo1;
            }
        }
        return null;
    }

    public void borrar (CicloFormativo cicloFormativo)throws OperationNotSupportedException{

        if (coleccionCiclos.contains(cicloFormativo)){
            coleccionCiclos.remove(cicloFormativo);
        }else {
            throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");

        }
    }


}
