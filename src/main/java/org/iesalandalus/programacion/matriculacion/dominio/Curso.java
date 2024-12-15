package org.iesalandalus.programacion.matriculacion.dominio;

public enum Curso {

    PRIMERO("PRIMERO"), SEGUNDO("SEGUNDO");

    String cadenaAMostrar;

    Curso(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir (){

        int digito=0;

        return digito + ".-"+cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
