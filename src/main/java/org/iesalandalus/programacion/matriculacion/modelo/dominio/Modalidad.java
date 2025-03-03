package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum Modalidad {

    PRESENCIAL("PRESENCIAL"),SEMIPRESENCIAL("SEMIPRESENCIAL");

    String cadenaAMostrar;

    Modalidad(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir(){

        return (ordinal()+1)+ " .- "+cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "Modalidad{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
