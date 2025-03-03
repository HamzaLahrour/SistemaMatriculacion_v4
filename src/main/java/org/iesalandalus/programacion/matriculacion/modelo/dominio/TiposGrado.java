package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum TiposGrado {
    GRADOD("GRADOD"),GRADOE("GRADOE");

    String cadenaAMostrar;

    TiposGrado(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }
    public String imprimir(){
        return (ordinal()+1) +" .- "+cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "TiposGrado{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }


}
