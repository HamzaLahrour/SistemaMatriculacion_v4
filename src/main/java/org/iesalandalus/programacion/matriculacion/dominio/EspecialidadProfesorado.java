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

        int digito=0;

        return digito + ".-"+cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "EspecialidadProfesorado{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
