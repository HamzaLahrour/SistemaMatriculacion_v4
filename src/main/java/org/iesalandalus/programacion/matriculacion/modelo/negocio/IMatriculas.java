package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IMatriculas {

    public void comenzar();
    public void terminar();
    public List<Matricula> get()throws OperationNotSupportedException;
    public int getTamano();
    public void insertar(Matricula matricula)throws OperationNotSupportedException;
    public Matricula buscar(Matricula matricula);
    public void borrar(Matricula matricula) throws OperationNotSupportedException;
    public List<Matricula> get(Alumno alumno);
    public List<Matricula> get(String cursoAcademico);
    public List<Matricula> get(CicloFormativo cicloFormativo);
}
