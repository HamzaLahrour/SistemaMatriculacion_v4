package org.iesalandalus.programacion.matriculacion.dominio;

public enum EspecialidadProfesorado {


    INFORMATICA("INFORMATICA"),
    SISTEMAS("SISTEMAS"),
    FOL("FOL");

    String cadenaAMostrar;

    private EspecialidadProfesorado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir (){

        return ordinal() + " .- "+cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "EspecialidadProfesorado{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
