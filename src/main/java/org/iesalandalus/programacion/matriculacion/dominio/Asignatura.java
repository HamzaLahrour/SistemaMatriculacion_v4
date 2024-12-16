package org.iesalandalus.programacion.matriculacion.dominio;

import java.util.Objects;

public class Asignatura {

    public final static int MAX_NUM_HORAS_ANUALES=300;
    public final static int MAX_NUM_HORAS_DESDOBLES=6;
    private final static String ER_CODIGO="[0-9]{4}";
    private String codigo;
    private String nombre;
    private int horasAnuales;
    Curso curso;
    private int horasDesdoble;
    EspecialidadProfesorado especialidadProfesorado;

    private CicloFormativo cicloFormativo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public CicloFormativo getCicloFormativo(){


        return cicloFormativo;
    }


    public void setCicloFormativo (CicloFormativo cicloFormativo){

        this.cicloFormativo=cicloFormativo;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHorasAnuales() {
        return horasAnuales;
    }

    public void setHorasAnuales(int horasAnuales) {
        this.horasAnuales = horasAnuales;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public EspecialidadProfesorado getEspecialidadProfesorado() {
        return especialidadProfesorado;
    }

    public void setEspecialidadProfesorado(EspecialidadProfesorado especialidadProfesorado) {
        this.especialidadProfesorado = especialidadProfesorado;
    }

    public int getHorasDesdoble() {
        return horasDesdoble;
    }

    public void setHorasDesdoble(int horasDesdoble) {
        this.horasDesdoble = horasDesdoble;
    }

    public Asignatura(String codigo, String nombre, int horasAnuales,  Curso curso,  int horasDesdoble  , EspecialidadProfesorado especialidadProfesorado, CicloFormativo cicloFormativo) {

        if (codigo==null){
            throw new NullPointerException("ERROR: El código de una asignatura no puede ser nulo.");

        }else if (codigo.isBlank()){

            throw new IllegalArgumentException("ERROR: El código de una asignatura no puede estar vacío.");
        } else if (!codigo.matches(ER_CODIGO)) {
            throw new IllegalArgumentException("ERROR: El código de la asignatura no tiene un formato válido.");

        } else if (nombre==null) {
            throw new NullPointerException("ERROR: El nombre de una asignatura no puede ser nulo.");
        } else if (nombre.isBlank()) {
            throw new IllegalArgumentException("ERROR: El nombre de una asignatura no puede estar vacío.");
        } else if (horasAnuales<=0 || horasAnuales>MAX_NUM_HORAS_ANUALES) {
            throw new IllegalArgumentException("ERROR: El número de horas de una asignatura no puede ser menor o igual a 0 ni mayor a 300.");
        } else if (curso==null) {
            throw new NullPointerException("ERROR: El curso de una asignatura no puede ser nulo.");
        } else if (horasDesdoble<=0 || horasDesdoble>MAX_NUM_HORAS_DESDOBLES) {
            throw new IllegalArgumentException("ERROR: El número de horas de desdoble de una asignatura no puede ser menor a 0 ni mayor a 6.");

        } else if (especialidadProfesorado==null) {
            throw new NullPointerException("ERROR: La especialidad del profesorado de una asignatura no puede ser nula.");
        } else if (cicloFormativo==null) {
            throw new NullPointerException("ERROR: El ciclo formativo de una asignatura no puede ser nulo.");

        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.horasAnuales = horasAnuales;
        this.especialidadProfesorado = especialidadProfesorado;
        this.curso = curso;
        this.horasDesdoble = horasDesdoble;
        this.cicloFormativo=cicloFormativo;

    }

    public Asignatura (Asignatura asignatura){


        if (asignatura==null){

            throw new NullPointerException("ERROR: No es posible copiar una asignatura nula.");

        }


        this.codigo = asignatura.codigo;
        this.nombre = asignatura.nombre;
        this.horasAnuales = asignatura.horasAnuales;
        this.especialidadProfesorado = asignatura.especialidadProfesorado;
        this.curso = asignatura.curso;
        this.horasDesdoble = asignatura.horasDesdoble;
        this.cicloFormativo=asignatura.cicloFormativo;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asignatura that = (Asignatura) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        return
                "Código=" + codigo  +
                ", nombre=" + nombre +
                ", horasAnuales=" + horasAnuales +
                ", curso=" + curso +
                ", horasDesdoble=" + horasDesdoble +
                ", ciclo formativo=" + cicloFormativo +
                        "nombre ciclo formativo=" + cicloFormativo.getNombre()
                ;
    }

    public String imprimir (){

        return "Código asignatura="+ codigo+ "," + " nombre asignatura="+ nombre + ","+ " ciclo formativo=" + cicloFormativo + "," + " nombre ciclo formativo=" + cicloFormativo.imprimir();
    }
}
