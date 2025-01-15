package org.iesalandalus.programacion.matriculacion.dominio;

public enum Curso {

    PRIMERO("PRIMERO"), SEGUNDO("SEGUNDO");

    String cadenaAMostrar;

     private Curso(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir (){
        return ordinal() + ".- "+cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
