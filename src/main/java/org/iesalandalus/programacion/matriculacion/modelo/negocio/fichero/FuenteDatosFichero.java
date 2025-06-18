package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;

public class FuenteDatosFichero implements IFuenteDatos {

    @Override
    public IAlumnos crearAlumnos() {
        return Alumnos.getInstancia();
    }

    @Override
    public IAsignaturas crearAsignaturas() {
        return Asignaturas.getInstancia();
    }

    @Override
    public ICiclosFormativos crearCiclosFormativos() {
        return CiclosFormativos.getInstancia();
    }

    @Override
    public IMatriculas crearMatriculas() {
        return Matriculas.getInstancia();
    }
}

