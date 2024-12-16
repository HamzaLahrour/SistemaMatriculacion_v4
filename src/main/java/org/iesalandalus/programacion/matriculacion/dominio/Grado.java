package org.iesalandalus.programacion.matriculacion.dominio;

public enum Grado {

    GDCFGB("GDCFGB"),
    GDCFGM("GDCFGM"),
    GDCFGS("GDCFGS");

    String cadenaAMostrar;

    private Grado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir (){
      int digito=0;

     return digito + ".-"+cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "Grado{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
