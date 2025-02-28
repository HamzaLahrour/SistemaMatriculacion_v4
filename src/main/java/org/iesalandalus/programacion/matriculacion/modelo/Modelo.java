package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;

import javax.naming.OperationNotSupportedException;

public class Modelo {


    private static Alumnos alumnos;
    private static Asignaturas asignaturas;
    private static CiclosFormativos ciclosFormativos;
    private static Matriculas matriculas;


    public void comenzar (){

        alumnos=new Alumnos();
        asignaturas= new Asignaturas();
        ciclosFormativos=new CiclosFormativos();
        matriculas=new Matriculas();

    }


    public void terminar (){
        System.out.println("El modelo ha terminado");
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


    public Alumno[] getAlumnos(){
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


    public Asignatura [] getAsignaturas(){
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


    public CicloFormativo [] getCiclosFormativos(){
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
    public Matricula [] getMatriculas()throws OperationNotSupportedException{
        return matriculas.get();
    }
    public Matricula [] getMatriculas(Alumno alumno){
        return matriculas.get(alumno);
    }
    public Matricula [] getMatriculas(CicloFormativo cicloFormativo){
        return matriculas.get(cicloFormativo);
    }

    public Matricula [] getMatriculas(String cursoAcademico){
        return matriculas.get(cursoAcademico);
    }


}

