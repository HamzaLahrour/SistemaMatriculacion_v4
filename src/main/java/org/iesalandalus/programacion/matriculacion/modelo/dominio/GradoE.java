package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public class GradoE extends Grado {
    private int numEdiciones;

    public GradoE(String nombre, int numAnios,int numEdiciones) {
        super(nombre);
        setNumAnios(numAnios);
        setNumEdiciones(numEdiciones);
    }
    public int getNumEdiciones(){
        return numEdiciones;
    }

    public void setNumEdiciones(int numEdiciones) {
        this.numEdiciones = numEdiciones;
    }

    @Override
    public void setNumAnios(int numAnios) {
        if (numAnios!=1){
            throw new IllegalArgumentException("ERROR: El numero de años de los GradoE es de 1 año.");
        }
        this.numAnios=numAnios;
    }

    @Override
    public String toString() {
        return "GradoE{" +
                "numEdiciones=" + numEdiciones +
                ", nombre='" + nombre + '\'' +
                ", iniciales='" + iniciales + '\'' +
                ", numAnios=" + numAnios +
                '}';
    }
}

