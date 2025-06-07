package org.iesalandalus.programacion.matriculacion.modelo.negocio;

public interface IFuenteDatos {
    public IAlumnos crearAlumnos();
    public IAsignaturas crearAsignaturas();
    public ICiclosFormativos crearCiclosFormativos();
    public IMatriculas crearMatriculas();
}
