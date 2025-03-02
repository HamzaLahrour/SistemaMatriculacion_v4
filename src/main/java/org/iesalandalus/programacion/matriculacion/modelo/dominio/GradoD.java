package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public class GradoD extends Grado {
    private Modalidad modalidad;

    public GradoD(String nombre,int numAnios, Modalidad modalidad) {
        super(nombre);
        setModalidad(modalidad);
        setNumAnios(numAnios);
    }

    public Modalidad getModalidad(){
     return modalidad;
    }

    public void setModalidad(Modalidad modalidad){

        if (modalidad==null){
            throw new NullPointerException("ERROR: La modalidad de un grado no puede ser nula.");
        }

        this.modalidad=modalidad;

    }

    @Override
    public void setNumAnios(int numAnios) {
        if (numAnios !=2 && numAnios!=3){
            throw new IllegalArgumentException("ERROR: ERROR: Los grados D solo pueden tener 2 o 3 años.");
        }
        this.numAnios=numAnios;
    }

    @Override
    public String toString() {
        return "GradoD{" +
                "modalidad=" + modalidad +
                ", nombre='" + nombre + '\'' +
                ", iniciales='" + iniciales + '\'' +
                ", numAnios=" + numAnios +
                '}';
    }
}
