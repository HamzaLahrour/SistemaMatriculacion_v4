package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Matriculas implements IMatriculas {

     List <Matricula> coleccionMatriculas = new ArrayList<>();

    public Matriculas() {

    }

    private List<Matricula> copiaProfundaMatriculas()throws OperationNotSupportedException{

        List<Matricula> matriculaCopia = new ArrayList<>();

        for (Matricula matricula: coleccionMatriculas){
            matriculaCopia.add(new Matricula(matricula));
        }
        return matriculaCopia;
    }

    @Override
    public void comenzar() {
        System.out.printf("Comenzar");
    }

    @Override
    public void terminar() {
        System.out.printf("Terminar");
    }

    public List<Matricula> get ()throws OperationNotSupportedException{

       List<Matricula> copiaMa=copiaProfundaMatriculas();
        if (copiaMa.isEmpty()){
            throw new IllegalArgumentException("ERROR:No hay matriculas registradas.");
        }
        return copiaMa;
    }

    @Override
    public int getTamano() {
        return 0;
    }

    public void insertar (Matricula matricula)throws OperationNotSupportedException {

        if (!coleccionMatriculas.contains(matricula)){
            coleccionMatriculas.add(matricula);
        }else {
            throw new IllegalArgumentException("ERROR:La matricula ya existe.");
        }
    }

    public Matricula buscar  (Matricula matricula){

        Iterator<Matricula> matriculaIterator = coleccionMatriculas.iterator();
        while (matriculaIterator.hasNext()){
            Matricula matricula1=matriculaIterator.next();
            if (matricula1.equals(matricula)){
                return matricula1;
            }
        }
        return null;
    }

    public void borrar (Matricula matricula)throws OperationNotSupportedException{

        if (coleccionMatriculas.contains(matricula)){
            coleccionMatriculas.remove(matricula);
        }else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");

        }

    }

    public List<Matricula> get (Alumno alumno){

        if (alumno==null){
            throw new NullPointerException("ERROR:Alumno no puede ser nulo.");
        }

        List<Matricula> coleccionMatriculasPorAlumno=new ArrayList<>();

        for (Matricula matricula : coleccionMatriculas){
            if (alumno.equals(matricula.getAlumno())){
                coleccionMatriculasPorAlumno.add(matricula);
            }
        }
       return coleccionMatriculasPorAlumno;

    }

    public List<Matricula> get (String cursoAcademico){

        if (cursoAcademico==null){
            throw new IllegalArgumentException("ERROR:El curso academico no puede ser nulo.");
        }
        List<Matricula> coleccionMatriCurso= new ArrayList<>();

        for (Matricula matricula: coleccionMatriculas){
            if (cursoAcademico.equals(matricula.getCursoAcademico())){
                coleccionMatriCurso.add(matricula);
            }
        }

        return coleccionMatriCurso;
    }


    public List<Matricula> get (CicloFormativo cicloFormativo){

        if (cicloFormativo==null){
            throw new NullPointerException("ERROR:El ciclo no puede ser nulo.");
        }

        List<Matricula> coleccionMatriculasCiclo=new ArrayList<>();

        for (Matricula matricula: coleccionMatriculas){

            for (Asignatura asignatura : matricula.getColeccionAsignaturas()){
                if (cicloFormativo.equals(asignatura.getCicloFormativo())){
                    coleccionMatriculasCiclo.add(matricula);
                }
            }

        }

        return coleccionMatriculasCiclo;

    }




}
