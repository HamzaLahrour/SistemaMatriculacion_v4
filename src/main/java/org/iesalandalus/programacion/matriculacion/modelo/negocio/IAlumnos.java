package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IAlumnos {
    public void comenzar();
    public void terminar();
    public List<Alumno> get();
    public int getTamano();
    public void insertar (Alumno alumno);
    public Alumno buscar(Alumno alumno);
    public void borrar(Alumno alumno) throws OperationNotSupportedException;
}
