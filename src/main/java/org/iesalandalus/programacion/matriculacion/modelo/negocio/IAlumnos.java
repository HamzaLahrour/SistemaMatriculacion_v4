package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;

import javax.naming.OperationNotSupportedException;

public interface IAlumnos {
    public void comenzar();
    public void terminar();
    public void insertar (Alumno alumno);
    public Alumno buscar(Alumno alumno);
    public void borrar(Alumno alumno) throws OperationNotSupportedException;
}
