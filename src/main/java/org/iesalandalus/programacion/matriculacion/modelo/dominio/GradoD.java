package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public class GradoD extends Grado {
    private Modalidad modalidad;

    public GradoD(String nombre,int numAnios, Modalidad modalidad) {
        super(nombre);
        setModalidad(modalidad);
        setnumAnios(numAnios);
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
    public void setnumAnios(int numAnios){
        if (numAnios != 2 && numAnios!=3){
            throw new IllegalArgumentException("ERROR: El número de años de un GradoD debe ser 2 o 3 años.");
        }
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
