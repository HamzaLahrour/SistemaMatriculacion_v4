package org.iesalandalus.programacion.matriculacion.vista;

import javax.swing.plaf.PanelUI;

public enum Opcion {

    SALIR("SALIR"){
        @Override
        public void ejecutar(){
            vista.terminar();
        }
    },


    INSERTAR_ALUMNO("INSERTAR_ALUMNO"){
        @Override
        public void ejecutar(){
            vista.insertarAlumno();
        }
    },
    BUSCAR_ALUMNO("BUSCAR_ALUMNO"){
        @Override
        public void ejecutar(){
            vista.buscarAlumno();
        }
    },
    BORRAR_ALUMNO("BORRAR_ALUMNO"){
        @Override
        public void ejecutar(){
            vista.borrarAlumno();
        }
    },
    MOSTRAR_ALUMNOS("MOSTRAR_ALUMNOS"){
        @Override
        public void ejecutar(){
            vista.mostrarAlumnos();
        }
    },
    INSERTAR_CICLO_FORMATIVO("INSERTAR_CICLO_FORMATIVO"){
        @Override
        public void ejecutar(){
            vista.insertarCiclosFormativos();
        }
    },
    BUSCAR_CICLO_FORMATIVO("BUSCAR_CICLO_FORMATIVO"){
        @Override
        public void ejecutar(){
            vista.buscarCicloFormartivo();
        }
    },
    BORRAR_CICLO_FORMATIVO("BORRAR_CICLO_FORMATIVO"){
        @Override
        public void ejecutar(){
            vista.borrarCicloFormativo();
        }
    },
    MOSTRAR_CICLOS_FORMATIVOS("MOSTRAR_CICLOS_FORMATIVOS"){
        @Override
        public void ejecutar(){
            vista.mostrarCiclosFormativos();
        }
    },
    INSERTAR_ASIGNATURA("INSERTAR_ASIGNATURA"){
        @Override
        public void ejecutar(){
            vista.insertarAsignatura();
        }
    },
    BUSCAR_ASIGNATURA("BUSCAR_ASIGNATURA"){
        @Override
        public void ejecutar(){
            vista.buscarAsignatura();
        }
    },
    BORRAR_ASIGNATURA("BORRAR_ASIGNATURA"){
        @Override
        public void ejecutar(){
            vista.borrarAsignatura();
        }
    },
    MOSTRAR_ASIGNATURAS("MOSTRAR_ASIGNATURAS"){
        @Override
        public void ejecutar(){
            vista.mostrarAsignaturas();
        }
    },
    INSERTAR_MATRICULA("INSERTAR_MATRICULA"){
        @Override
        public void ejecutar(){
            vista.insertarMatricula();
        }
    },
    BUSCAR_MATRICULA("BUSCAR_MATRICULA"){
        @Override
        public void ejecutar(){
            vista.buscarMatricula();
        }
    },
    ANULAR_MATRICULA("ANULAR_MATRICULA"){
        @Override
        public void ejecutar(){
            vista.anularMatricula();
        }
    },
    MOSTRAR_MATRICULAS("MOSTRAR_MATRICULAS"){
        @Override
        public void ejecutar(){
            vista.mostrarMatriculas();
        }
    },
    MOSTRAR_MATRICULAS_ALUMNO("MOSTRAR_MATRICULAS_ALUMNO"){
        @Override
        public void ejecutar(){
            vista.mostrarMatriculasPorAlumno();
        }
    },
    MOSTRAR_MATRICULAS_CICLO_FORMATIVO("MOSTRAR_MATRICULAS_CICLO_FORMATIVO"){
        @Override
        public void ejecutar(){
            vista.mostrarMatriculasPorCicloFormativo();
        }
    },
    MOSTRAR_MATRICULAS_CURSO_ACADEMICO("MOSTRAR_MATRICULAS_CURSO_ACADEMICO"){
        @Override
        public void ejecutar(){
            vista.mostrarMatriculasPorCursoAcademico();
        }
    };

    String cadenaAMostrar;
    private static Vista vista;

    public static void setVista(Vista vista){

        Opcion.vista=vista;
    }

    public abstract void ejecutar ();


    Opcion(String cadenaAMostrar) {
        this.cadenaAMostrar=cadenaAMostrar;

    }

    @Override
    public String toString() {
        return ordinal() + " .- " + cadenaAMostrar;
    }
}
