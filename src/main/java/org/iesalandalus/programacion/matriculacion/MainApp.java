package org.iesalandalus.programacion.matriculacion;


import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;
import org.iesalandalus.programacion.matriculacion.vista.Consola;
import org.iesalandalus.programacion.matriculacion.vista.Opcion;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;


public class MainApp {
    public static final int CAPACIDAD=3;

    private Alumnos alumnos;

    private Asignaturas asignaturas;
    private CiclosFormativos ciclosFormativos;
    private Matriculas matriculas;

    public MainApp() {
        alumnos = new Alumnos(5);
        asignaturas = new Asignaturas(6);
        ciclosFormativos= new CiclosFormativos(20);
        matriculas= new Matriculas(3);
    }

    private void ejecutarOpcion (Opcion opcion){


        Asignaturas asignaturas=new Asignaturas(5);
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

            alumnos.insertar(alumno);
            System.out.println("Alumno insertado correctamente!");

        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e ){
            System.out.println(e.getMessage());
        }
    }
    private void buscarAlumno (){


        System.out.println("*** BUSQUEDA DE ALUMNOS ***");
        Alumno alumno;


        alumno=Consola.getAlumnoPorDni();


        System.out.println(alumnos.buscar(alumno));




    }
    private void borrarAlumno (){

        System.out.println("*** ELIMINAR ALUMNO ***");

        Alumno alumno;


        alumno=Consola.getAlumnoPorDni();

        try {
            alumnos.borrar(alumno);
        }catch (OperationNotSupportedException | NullPointerException e){

            System.out.println(e.getMessage());
        }
    }

    private void mostrarAlumnos (){

        System.out.println("*** MOSTRAR ALUMNOS ***");

        for (Alumno alumno : alumnos.get()){
            System.out.println(alumno);
        }
    }

    private void insertarAsignatura (){
        System.out.println("*** INSERTAR ASIGNATURA ***");

        Asignatura asignatura;

        asignatura=Consola.leerAsignatura(ciclosFormativos);

        try {
            asignaturas.insertar(asignatura);
        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }

    }

    private void buscarAsignatura (){

        System.out.println("*** BUSCAR ASIGNATURAS ***");

        Asignatura asignatura;

        asignatura=Consola.getAsignaturaPorCodigo();

        System.out.println( asignaturas.buscar(asignatura));

    }

    private void borrarAsignatura (){


        System.out.println("*** ELIMINAR ASIGNATURA ***");
        Asignatura asignatura;

        asignatura=Consola.getAsignaturaPorCodigo();

        try {
            asignaturas.borrar(asignatura);
        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }

    }

    private void mostrarAsignaturas (){

        System.out.println("*** MOSTRAR ASIGNATURAS ***");

        for (Asignatura asignatura : asignaturas.get()){
            System.out.println(asignatura);
        }

    }

    private void insertarCiclosFormativos (){

        System.out.println("*** INSERTAR CICLOS FORMATIVOS ***");

        CicloFormativo cicloFormativo;

        cicloFormativo=Consola.leerCicloFormativo();

        try {

            ciclosFormativos.insertar(cicloFormativo);

        }catch (OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }

    }

    private void buscarCicloFormartivo (){

        System.out.println("*** BUSCAR CICLO FORMATIVO ***");

        CicloFormativo cicloFormativo;
        cicloFormativo=Consola.getCicloFormativoPorCodigo();
        System.out.println(ciclosFormativos.buscar(cicloFormativo));
    }

    private void borrarCicloFormativo (){

        System.out.println("*** ELIMINAR CICLO FORMATIVO ***");

        CicloFormativo cicloFormativo;

        cicloFormativo=Consola.getCicloFormativoPorCodigo();

        try {
            ciclosFormativos.borrar(cicloFormativo);

        }catch (OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }

    }

    private void mostrarCiclosFormativos (){

        System.out.println("*** MOSTRAR CICLOS FORMATIVOS ***");

        for (CicloFormativo cicloFormativo : ciclosFormativos.get()){

            System.out.println(cicloFormativo);

        }
    }

    private void insertarMatricula (){


        System.out.println("*** INSERTAR MATRICULA ***");
        Matricula matricula;
        try {
            matricula=Consola.leerMatricula(alumnos,asignaturas);
            matriculas.insertar(matricula);
        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void buscarMatricula (){


        System.out.println("*** BUSCAR MATRICULA ***");

        Matricula matricula;

        try {
            matricula=Consola.getMatriculaPorIdentificador();

            System.out.println(matriculas.buscar(matricula));

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
            for (Matricula matricula1 : matriculas.get()){
                System.out.println(matricula1);
            }

        }catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }

        try {

            matricula=Consola.getMatriculaPorIdentificador();
            fechaAnulacion=Consola.leerFecha("Introduzca la fecha en la que has anulado la matricula en formato dd/MM/yyy: ");
            matricula2= matriculas.buscar(matricula);
            matricula2.setFechaAnulacion(fechaAnulacion);

            System.out.println("Matricula anulada correctamente!.");


        }catch (OperationNotSupportedException | IllegalArgumentException |NullPointerException e){
            System.out.println(e.getMessage());
        }

    }

    private void mostrarMatriculas (){


        System.out.println("*** MOSTRAR MATRICULAS ***");

        try {
            for (Matricula matricula : matriculas.get()){

                System.out.println(matricula);
            }

        }catch (OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }

    }

    private void mostrarMatriculasPorAlumno (){

        System.out.println("*** MOSTRAR MATRICULAS POR ALUMNO ***");

        Alumno alumno;
        alumno=Consola.getAlumnoPorDni();
        for (Matricula matricula : matriculas.get(alumno)){

            System.out.println(matricula);

        }


    }

    private void mostrarMatriculasPorCursoAcademico (){

        System.out.println("*** MOSTRAR MATRICULAS POR CURSO ACADEMICO ***");

        String cursoAcademico;

        System.out.println("Introduce el curso academico para realizar la busqueda: ");
        cursoAcademico=Entrada.cadena();

        for (Matricula matricula : matriculas.get(cursoAcademico)){
            System.out.println(matricula);
        }
    }

    private void mostrarMatriculasPorCicloFormativo (){


        System.out.println("*** MOSTRAR MATRICULAS POR CICLO FORMATIVO ***");

        CicloFormativo cicloFormativo;

        cicloFormativo=Consola.getCicloFormativoPorCodigo();

        for (Matricula matricula : matriculas.get(cicloFormativo)){
            System.out.println(matricula);

        }


    }





    public static void main(String[] args) throws OperationNotSupportedException {


        Alumnos alumnos = new Alumnos(5);
        MainApp app = new MainApp();

       Opcion opcion;
       opcion=Opcion.BORRAR_ALUMNO;
       Opcion opcion1;
       opcion1=Opcion.SALIR;

       while (opcion!=opcion1){

           Consola.mostrarMenu();

           opcion=Consola.elegirOpcion();
           app.ejecutarOpcion(opcion);


       }



    }










    }




