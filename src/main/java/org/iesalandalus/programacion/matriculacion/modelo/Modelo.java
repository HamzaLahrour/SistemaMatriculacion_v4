package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Matriculas;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public class Modelo {


    //private static Alumnos alumnos;
    //private static Asignaturas asignaturas;
    //private static CiclosFormativos ciclosFormativos;
    //private static Matriculas matriculas;
    private IFuenteDatos fuenteDatos;

    private IAlumnos alumnos;
    private IAsignaturas asignaturas;
    private ICiclosFormativos ciclosFormativos;
    private IMatriculas matriculas;

    public Modelo(FactoriaFuenteDatos factoriaFuenteDatos){
        if (factoriaFuenteDatos == null) {
            throw new IllegalArgumentException("ERROR: La factoría de fuente de datos no puede ser nula.");
        }
        // Se establece la fuente de datos utilizando la factoría
        setFuenteDatos(factoriaFuenteDatos.crear());
    }

    private void setFuenteDatos(IFuenteDatos fuenteDatos){
        if (fuenteDatos == null) {
            throw new NullPointerException("ERROR: La fuente de datos no puede ser nula.");
        }
        this.fuenteDatos = fuenteDatos;
    }

    public void comenzar (){

        if (fuenteDatos == null) {
            throw new IllegalStateException("ERROR: La fuente de datos no está configurada.");
        }

        // Crear las colecciones desde la fuente de datos
        alumnos = fuenteDatos.crearAlumnos();
        asignaturas = fuenteDatos.crearAsignaturas();
        ciclosFormativos = fuenteDatos.crearCiclosFormativos();
        matriculas = fuenteDatos.crearMatriculas();

        //Metodo comenzar de cada coleccion
        alumnos.comenzar();
        asignaturas.comenzar();
        ciclosFormativos.comenzar();
        matriculas.comenzar();

    }


    public void terminar (){
        if (alumnos != null) {
            alumnos.terminar();
        }
        if (asignaturas != null) {
            asignaturas.terminar();
        }
        if (ciclosFormativos != null) {
            ciclosFormativos.terminar();
        }
        if (matriculas != null) {
            matriculas.terminar();
        }
    }


    public void insertar(Alumno alumno)throws OperationNotSupportedException {
        alumnos.insertar(alumno);
    }

    public Alumno buscar(Alumno alumno){
        return alumnos.buscar(alumno);
    }
    public void borrar (Alumno alumno)throws OperationNotSupportedException{
        alumnos.borrar(alumno);
    }


    public List<Alumno> getAlumnos(){
        return alumnos.get();
    }


    public void insertar(Asignatura asignatura)throws OperationNotSupportedException{
        asignaturas.insertar(asignatura);
    }

    public Asignatura buscar (Asignatura asignatura){

        return asignaturas.buscar(asignatura);
    }

    public void borrar (Asignatura asignatura)throws OperationNotSupportedException{
        asignaturas.borrar(asignatura);
    }


    public List<Asignatura> getAsignaturas(){
        return asignaturas.get();
    }

    public void insertar(CicloFormativo cicloFormativo)throws OperationNotSupportedException{
        ciclosFormativos.insertar(cicloFormativo);
    }

    public CicloFormativo buscar(CicloFormativo cicloFormativo){
        return ciclosFormativos.buscar(cicloFormativo);
    }

    public void borrar(CicloFormativo cicloFormativo)throws OperationNotSupportedException{
        ciclosFormativos.borrar(cicloFormativo);
    }


    public List<CicloFormativo> getCiclosFormativos(){
        return ciclosFormativos.get();
    }


    public void insertar(Matricula matricula)throws OperationNotSupportedException{

        matriculas.insertar(matricula);
    }

    public Matricula buscar(Matricula matricula){
        return matriculas.buscar(matricula);
    }

    public void borrar(Matricula matricula)throws OperationNotSupportedException{
        matriculas.borrar(matricula);
    }
    public List<Matricula> getMatriculas()throws OperationNotSupportedException{
        return matriculas.get();
    }
    public List<Matricula> getMatriculas(Alumno alumno){
        return matriculas.get(alumno);
    }
    public List<Matricula> getMatriculas(CicloFormativo cicloFormativo){
        return matriculas.get(cicloFormativo);
    }

    public List<Matricula> getMatriculas(String cursoAcademico){
        return matriculas.get(cursoAcademico);
    }


}

