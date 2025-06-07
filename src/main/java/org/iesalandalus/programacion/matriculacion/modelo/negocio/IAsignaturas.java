package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IAsignaturas {
    public void comenzar();
    public void terminar();
    public List<Asignatura> get();
    public int getTamano();
    public void insertar(Asignatura asignatura)throws OperationNotSupportedException;
    public Asignatura buscar(Asignatura asignatura);
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException;
}
