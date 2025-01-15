package org.iesalandalus.programacion.matriculacion.dominio;

public enum Grado {

    GDCFGB("Grado D Ciclo Formativo de Grado Básico"),
    GDCFGM(" Grado D Ciclo formativo de Grado Medio"),
    GDCFGS("Grado D Ciclo Formativo de Grado Superior");

    String cadenaAMostrar;

    private Grado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir (){

     return this.ordinal() + ".- "+ cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "Grado{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
