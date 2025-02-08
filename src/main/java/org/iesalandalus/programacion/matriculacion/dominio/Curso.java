package org.iesalandalus.programacion.matriculacion.dominio;

public enum Curso {

    PRIMERO("PRIMERO"), SEGUNDO("SEGUNDO");

    String cadenaAMostrar;

     private Curso(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir (){
        return  (ordinal() + 1 ) + ".- "+cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
