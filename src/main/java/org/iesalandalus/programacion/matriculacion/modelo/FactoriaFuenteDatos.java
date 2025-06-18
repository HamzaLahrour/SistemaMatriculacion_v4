package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.FuenteDatosFichero;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.FuenteDatosMySQL;

public enum FactoriaFuenteDatos {

    MEMORIA {
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosMemoria();
        }
    },
    MYSQL {
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosMySQL();
        }
    },FICHERO {
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosFichero();
        }
    };

    public abstract IFuenteDatos crear();
}
