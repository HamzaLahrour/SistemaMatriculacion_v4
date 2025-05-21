package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class Vista {


    private Controlador controlador;


    public Vista (){

        Opcion.setVista(this);
    }

    public void setControlador (Controlador controlador){

        if (controlador==null){
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }

        this.controlador=controlador;
    }

    public void comenzar (){


                Opcion opcion;
                opcion=Opcion.BORRAR_ALUMNO;
                Opcion opcion1;
                opcion1=Opcion.SALIR;


                while (opcion!=opcion1){
                    Consola.mostrarMenu();

                    try {
                        opcion=Consola.elegirOpcion();
                        opcion.ejecutar();
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }

                }

                controlador.terminar();

    }

    public void terminar(){

        System.out.println("Vista terminada.");
    }



    public void insertarAlumno(){

        System.out.println("*** INSERTAR ALUMNO ***");

        Alumno alumno;

        try {
            alumno=Consola.leerAlumno();

            controlador.insertar(alumno);
            System.out.println("Alumno insertado correctamente!");

        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e ){
            System.out.println(e.getMessage());
        }
    }
    public void buscarAlumno (){


        System.out.println("*** BUSQUEDA DE ALUMNOS ***");
        Alumno alumno;


        alumno=Consola.getAlumnoPorDni();


        System.out.println(controlador.buscar(alumno));




    }
    public void borrarAlumno (){

        System.out.println("*** ELIMINAR ALUMNO ***");

        Alumno alumno;


        alumno=Consola.getAlumnoPorDni();

        try {
            controlador.borrar(alumno);
        }catch (OperationNotSupportedException | NullPointerException e){

            System.out.println(e.getMessage());
        }

        System.out.println("Alumno eliminado correctamente!");
    }

    public void mostrarAlumnos (){

        System.out.println("*** LISTADO DE ALUMNOS ***");

        List<Alumno> alumnosOrdenados =controlador.getAlumnos().stream()
                .sorted(Comparator.comparing(Alumno::getNombre)).
                toList();



        alumnosOrdenados.forEach(System.out::println);
    }

    public void insertarAsignatura (){
        System.out.println("*** INSERTAR ASIGNATURA ***");

        Asignatura asignatura;
        CicloFormativo cicloFormativo=null;
        CicloFormativo cicloFormativo1;

        Consola.mostrarCiclosFormativos(controlador.getCiclosFormativos());
        cicloFormativo=Consola.getCicloFormativoPorCodigo();
        cicloFormativo1=controlador.buscar(cicloFormativo);

        //asignatura=Consola.leerAsignatura(cicloFormativo1);

        try {
            asignatura=Consola.leerAsignatura(cicloFormativo1);
            controlador.insertar(asignatura);
            System.out.println("Asignatura insertada correctamente!");
        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }



    }

    public void buscarAsignatura (){

        System.out.println("*** BUSCAR ASIGNATURAS ***");

        Asignatura asignatura;

        asignatura=Consola.getAsignaturaPorCodigo();

        System.out.println( controlador.buscar(asignatura));

    }

    public void borrarAsignatura (){


        System.out.println("*** ELIMINAR ASIGNATURA ***");
        Asignatura asignatura;

        asignatura=Consola.getAsignaturaPorCodigo();

        try {
            controlador.borrar(asignatura);
            System.out.println("Asignatura eliminada correctamente!");
        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }



    }

    public void mostrarAsignaturas (){

        System.out.println("*** MOSTRAR ASIGNATURAS ***");

        List<Asignatura> asignaturasOrdenadas = controlador.getAsignaturas().stream()
                .sorted(Comparator.comparing(Asignatura::getNombre)).
                toList();

        asignaturasOrdenadas.forEach(System.out::println);

    }

    public void insertarCiclosFormativos (){

        System.out.println("*** INSERTAR CICLOS FORMATIVOS ***");

        CicloFormativo cicloFormativo;

        cicloFormativo=Consola.leerCicloFormativo();

        try {

            controlador.insertar(cicloFormativo);
            System.out.println("CIclo formativo insertado correctamente!");

        }catch (OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }


    }

    public void buscarCicloFormartivo (){

        System.out.println("*** BUSCAR CICLO FORMATIVO ***");

        CicloFormativo cicloFormativo;
        cicloFormativo=Consola.getCicloFormativoPorCodigo();
        System.out.println(controlador.buscar(cicloFormativo));
    }

    public void borrarCicloFormativo (){

        System.out.println("*** ELIMINAR CICLO FORMATIVO ***");

        CicloFormativo cicloFormativo;

        cicloFormativo=Consola.getCicloFormativoPorCodigo();

        try {
            controlador.borrar(cicloFormativo);
            System.out.println("CicloFormativo eliminado correctamente!");

        }catch (OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }


    }

    public void mostrarCiclosFormativos (){

        System.out.println("*** MOSTRAR CICLOS FORMATIVOS ***");

       List<CicloFormativo> cicloFormativosOrdenados=controlador.getCiclosFormativos().stream()
               .sorted(Comparator.comparing(CicloFormativo::getNombre)).toList();

       cicloFormativosOrdenados.forEach(System.out::println);
    }

    public void insertarMatricula (){


        System.out.println("*** INSERTAR MATRICULA ***");
        Matricula matricula;
        Alumno alumno=null;
        Alumno alumno1=null;

        List<Asignatura> asignaturas= controlador.getAsignaturas();

        mostrarAlumnos();

        try {
            alumno=Consola.getAlumnoPorDni();
            alumno1=controlador.buscar(alumno);

            matricula=Consola.leerMatricula(alumno1,asignaturas);
            controlador.insertar(matricula);
            System.out.println("Matricula insertada correctamente!");

        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }


    }

    public void buscarMatricula (){


        System.out.println("*** BUSCAR MATRICULA ***");

        Matricula matricula;

        try {
            matricula=Consola.getMatriculaPorIdentificador();

            System.out.println(controlador.buscar(matricula));

        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }

    }

    public void anularMatricula (){

        System.out.println("*** ANULAR MATRICULA ***");

        Matricula matricula;
        Matricula matricula2;
        LocalDate fechaAnulacion;



        try {
            for (Matricula matricula1 : controlador.getMatriculas()){
                System.out.println(matricula1);
            }

        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
            return;

        }



        try {

            matricula=Consola.getMatriculaPorIdentificador();
            fechaAnulacion=Consola.leerFecha("Introduzca la fecha en la que has anulado la matricula en formato dd/MM/yyy: ");
            matricula2= controlador.buscar(matricula);
            matricula2.setFechaAnulacion(fechaAnulacion);

            System.out.println("Matricula anulada correctamente!.");


        }catch (OperationNotSupportedException | IllegalArgumentException |NullPointerException e){
            System.out.println(e.getMessage());
        }

    }

    public void mostrarMatriculas (){


        System.out.println("*** MOSTRAR MATRICULAS ***");

        try {
            List<Matricula> matriculasOrdenadas = controlador.getMatriculas().stream()
                    .sorted(Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                            .thenComparing(matricula -> matricula.getAlumno().getNombre()))
                    .toList();


            matriculasOrdenadas.forEach(System.out::println);
        }catch (OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }

    }

    public void mostrarMatriculasPorAlumno (){

        System.out.println("*** MOSTRAR MATRICULAS POR ALUMNO ***");

        Alumno alumno;
        alumno=Consola.getAlumnoPorDni();
        for (Matricula matricula : controlador.getMatriculas(alumno)){

            System.out.println(matricula);

        }


    }

    public void mostrarMatriculasPorCursoAcademico (){

        System.out.println("*** MOSTRAR MATRICULAS POR CURSO ACADEMICO ***");

        String cursoAcademico;

        System.out.println("Introduce el curso academico para realizar la busqueda: ");
        cursoAcademico= Entrada.cadena();

        for (Matricula matricula : controlador.getMatriculas(cursoAcademico)){
            System.out.println(matricula);
        }
    }

    public void mostrarMatriculasPorCicloFormativo (){


        System.out.println("*** MOSTRAR MATRICULAS POR CICLO FORMATIVO ***");

        CicloFormativo cicloFormativo;

        cicloFormativo=Consola.getCicloFormativoPorCodigo();

        for (Matricula matricula : controlador.getMatriculas(cicloFormativo)){
            System.out.println(matricula);

        }


    }



}
