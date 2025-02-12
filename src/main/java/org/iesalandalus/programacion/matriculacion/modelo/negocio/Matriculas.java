package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import javax.naming.OperationNotSupportedException;

public class Matriculas {

    private  int capacidad;
    private int tamano;

     Matricula[] coleccionMatriculas;

    public Matriculas(int capacidad) {

        if (capacidad<=0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad = capacidad;
        this.coleccionMatriculas = new Matricula[capacidad];
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getTamano(){
        return tamano;
    }

    private Matricula[] copiaProfundaMatriculas()throws OperationNotSupportedException{

        Matricula[] copiaProfundaMatriculas=new Matricula[tamano];

        for (int i=0;i<tamano;i++){

            copiaProfundaMatriculas[i]=new Matricula(coleccionMatriculas[i]);

        }
        return copiaProfundaMatriculas;
    }

    public Matricula [] get ()throws OperationNotSupportedException{

        if (copiaProfundaMatriculas().length==0){
            throw new IllegalArgumentException("ERROR: No hay matriculas registradas.");
        }

        return copiaProfundaMatriculas();
    }

    public void insertar (Matricula matricula)throws OperationNotSupportedException {

        if (matricula==null){
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        for (int i=0;i<tamano;i++){

            if (coleccionMatriculas[i].equals(matricula)){

                throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
            }

        }

        if (tamano==capacidad){
            throw new OperationNotSupportedException("ERROR: No se aceptan más matrículas.");
        }

        if (tamano<capacidad){

            coleccionMatriculas[tamano]=new Matricula(matricula);
            tamano++;

        }
    }

    public Matricula buscar  (Matricula matricula){

        for (int i=0;i<tamano;i++){

            if (coleccionMatriculas[i].equals(matricula)){
                return coleccionMatriculas[i];
            }

        }
        return null;
    }

    public void borrar (Matricula matricula)throws OperationNotSupportedException{

        if (matricula==null){
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }

        for (int i=0;i<tamano;i++){
            if (coleccionMatriculas[i].equals(matricula)){
                for (int j=i;j<tamano-1;j++){
                    coleccionMatriculas[j]=coleccionMatriculas[j+1];
                }
                tamano--;

                return;
            }
        }
        throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
    }

    public Matricula [] get (Alumno alumno){

        int contador=0;

        for (int i=0;i<tamano;i++){

            if (coleccionMatriculas[i].getAlumno().equals(alumno)){
                contador++;
            }

        }

        if (contador==0){
            throw new IllegalArgumentException("ERROR: El alumno introducido no está matriculado.");
        }

        Matricula [] matriculasAlumno = new Matricula[contador];
        int indice=0;

        for (int i=0; i<tamano;i++){

            if (coleccionMatriculas[i].getAlumno().equals(alumno)){
                matriculasAlumno[indice++]=coleccionMatriculas[i];
            }
        }

        return matriculasAlumno;

    }

    public Matricula [] get (String cursoAcademico){

        int contador=0;

        for (int i=0;i<tamano;i++){
            if (coleccionMatriculas[i].getCursoAcademico().equals(cursoAcademico)){
                contador++;
            }
        }

        if (contador==0){
            throw new IllegalArgumentException("ERROR: No hay matriculas con el curso academico introducido.");
        }

        Matricula [] matriculasCursoAcademico = new Matricula[contador];
        int indice=0;

        for (int i=0;i<tamano;i++){

            if (coleccionMatriculas[i].getCursoAcademico().equals(cursoAcademico)){
                matriculasCursoAcademico[indice]=coleccionMatriculas[i];
                indice++;
            }
        }

        return matriculasCursoAcademico;
    }


    public Matricula [] get (CicloFormativo cicloFormativo){

        int contador=0;

        for (int i=0;i<tamano;i++){
            Asignatura [] asignaturasPrueba = coleccionMatriculas[i].getColeccionAsignaturas();

            for (Asignatura asignatura : asignaturasPrueba){

                if (asignatura.getCicloFormativo().equals(cicloFormativo)){
                    contador++;
                }
            }
        }

        if (contador==0){
            throw new IllegalArgumentException("ERROR: No existe ninguna matricula con el ciclo formativo introducido.");
        }

        Matricula [] matriculasPorCicloFormativo = new Matricula[contador];
        int indice=0;

        for (int i=0;i<tamano;i++){

            Asignatura [] asignaturasPrueba= coleccionMatriculas[i].getColeccionAsignaturas();

            for (Asignatura asignatura : asignaturasPrueba){

                if (asignatura.getCicloFormativo().equals(cicloFormativo)){

                    matriculasPorCicloFormativo[indice++]=coleccionMatriculas[i];

                }

            }

        }

        return matriculasPorCicloFormativo;
    }




}
