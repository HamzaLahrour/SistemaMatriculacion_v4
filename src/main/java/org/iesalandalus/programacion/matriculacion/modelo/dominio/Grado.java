package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public class Grado {
    protected String nombre;
    protected String iniciales;
    protected int numAnios;

    public Grado(String nombre) {
        setNombre(nombre);
        setIniciales();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre==null){
            throw new IllegalArgumentException("ERROR: El nombre de un grado no puede ser nulo.");
        }

        if (nombre.isBlank()){
            throw new IllegalArgumentException("ERROR: El nombre de un grado no puede estar vacio.");
        }

        this.nombre = nombre;
    }
    private void setIniciales(){

        String[] palabras = nombre.split("\\s");

        String c = "";
        for (int i = 0; i < palabras.length; i++) {
            if (!palabras[i].equals(""))
                c = c + palabras[i].charAt(0);
        }

        String incial;

        incial=c.toUpperCase();

        this.iniciales=incial;


    }

    @Override
    public String toString() {
        return  "(" + iniciales + ") - " + nombre;
    }

    public void setNumAnios(int numAnios){
        if (numAnios<=0){
            throw new IllegalArgumentException("ERROR: El número de años de un grado debe ser mayor que 0.");
        }
        this.numAnios=numAnios;
    }
}
