package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Vista {
    private Controlador controlador;


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
                        ejecutarOpcion(opcion);
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }

                }

                controlador.terminar();

    }

    public void terminar(){

        System.out.println("Vista terminada.");
    }

    private void ejecutarOpcion (Opcion opcion){



        switch (opcion){
            case INSERTAR_ALUMNO -> insertarAlumno();
            case BUSCAR_ALUMNO -> buscarAlumno();
            case BORRAR_ALUMNO -> borrarAlumno();
            case MOSTRAR_ALUMNOS -> mostrarAlumnos();
            case INSERTAR_CICLO_FORMATIVO -> insertarCiclosFormativos();
            case BUSCAR_CICLO_FORMATIVO -> buscarCicloFormartivo();
            case BORRAR_CICLO_FORMATIVO -> borrarCicloFormativo();
            case MOSTRAR_CICLOS_FORMATIVOS -> mostrarCiclosFormativos();
            case INSERTAR_ASIGNATURA -> insertarAsignatura();
            case BUSCAR_ASIGNATURA -> buscarAsignatura();
            case BORRAR_ASIGNATURA -> borrarAsignatura();
            case MOSTRAR_ASIGNATURAS -> mostrarAsignaturas();
            case INSERTAR_MATRICULA -> insertarMatricula();
            case BUSCAR_MATRICULA -> buscarMatricula();
            case ANULAR_MATRICULA -> anularMatricula();
            case MOSTRAR_MATRICULAS -> mostrarMatriculas();
            case MOSTRAR_MATRICULAS_ALUMNO -> mostrarMatriculasPorAlumno();
            case MOSTRAR_MATRICULAS_CICLO_FORMATIVO -> mostrarMatriculasPorCicloFormativo();
            case MOSTRAR_MATRICULAS_CURSO_ACADEMICO -> mostrarMatriculasPorCursoAcademico();

        }


    }

    private void insertarAlumno(){

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
    private void buscarAlumno (){


        System.out.println("*** BUSQUEDA DE ALUMNOS ***");
        Alumno alumno;


        alumno=Consola.getAlumnoPorDni();


        System.out.println(controlador.buscar(alumno));




    }
    private void borrarAlumno (){

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

    private void mostrarAlumnos (){

        System.out.println("*** LISTADO DE ALUMNOS ***");

        List<Alumno> alumnosOrdenados =controlador.getAlumnos().stream()
                .sorted(Comparator.comparing(Alumno::getNombre)).
                toList();



        alumnosOrdenados.forEach(System.out::println);
    }

    private void insertarAsignatura (){
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

    private void buscarAsignatura (){

        System.out.println("*** BUSCAR ASIGNATURAS ***");

        Asignatura asignatura;

        asignatura=Consola.getAsignaturaPorCodigo();

        System.out.println( controlador.buscar(asignatura));

    }

    private void borrarAsignatura (){


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

    private void mostrarAsignaturas (){

        System.out.println("*** MOSTRAR ASIGNATURAS ***");

        List<Asignatura> asignaturasOrdenadas = controlador.getAsignaturas().stream()
                .sorted(Comparator.comparing(Asignatura::getNombre)).
                toList();

        asignaturasOrdenadas.forEach(System.out::println);

    }

    private void insertarCiclosFormativos (){

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

    private void buscarCicloFormartivo (){

        System.out.println("*** BUSCAR CICLO FORMATIVO ***");

        CicloFormativo cicloFormativo;
        cicloFormativo=Consola.getCicloFormativoPorCodigo();
        System.out.println(controlador.buscar(cicloFormativo));
    }

    private void borrarCicloFormativo (){

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

    private void mostrarCiclosFormativos (){

        System.out.println("*** MOSTRAR CICLOS FORMATIVOS ***");

       List<CicloFormativo> cicloFormativosOrdenados=controlador.getCiclosFormativos().stream()
               .sorted(Comparator.comparing(CicloFormativo::getNombre)).toList();

       cicloFormativosOrdenados.forEach(System.out::println);
    }

    private void insertarMatricula (){


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

    private void buscarMatricula (){


        System.out.println("*** BUSCAR MATRICULA ***");

        Matricula matricula;

        try {
            matricula=Consola.getMatriculaPorIdentificador();

            System.out.println(controlador.buscar(matricula));

        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }

    }

    private void anularMatricula (){

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

    private void mostrarMatriculas (){


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

    private void mostrarMatriculasPorAlumno (){

        System.out.println("*** MOSTRAR MATRICULAS POR ALUMNO ***");

        Alumno alumno;
        alumno=Consola.getAlumnoPorDni();
        for (Matricula matricula : controlador.getMatriculas(alumno)){

            System.out.println(matricula);

        }


    }

    private void mostrarMatriculasPorCursoAcademico (){

        System.out.println("*** MOSTRAR MATRICULAS POR CURSO ACADEMICO ***");

        String cursoAcademico;

        System.out.println("Introduce el curso academico para realizar la busqueda: ");
        cursoAcademico= Entrada.cadena();

        for (Matricula matricula : controlador.getMatriculas(cursoAcademico)){
            System.out.println(matricula);
        }
    }

    private void mostrarMatriculasPorCicloFormativo (){


        System.out.println("*** MOSTRAR MATRICULAS POR CICLO FORMATIVO ***");

        CicloFormativo cicloFormativo;

        cicloFormativo=Consola.getCicloFormativoPorCodigo();

        for (Matricula matricula : controlador.getMatriculas(cicloFormativo)){
            System.out.println(matricula);

        }


    }



}
