package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface ICiclosFormativos {
    public void comenzar();
    public void terminar();
    public List<CicloFormativo> get();
    public int getTamano();
    public void insertar(CicloFormativo cicloFormativo)throws OperationNotSupportedException;
    public CicloFormativo buscar(CicloFormativo cicloFormativo);
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException;
}
