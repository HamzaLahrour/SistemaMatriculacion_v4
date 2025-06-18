package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Matriculas implements IMatriculas {

     List <Matricula> coleccionMatriculas;

    private Matriculas() {
        coleccionMatriculas = new ArrayList<>();
    }

    private static Matriculas instancia;

    private static final String RUTA_XML = "datos/matriculas.xml";

    public static Matriculas getInstancia() {
        if (instancia == null) {
            instancia = new Matriculas();
        }
        return instancia;
    }

    private void leerXML() {
        coleccionMatriculas.clear();
        File fichero = new File(RUTA_XML);
        if (!fichero.exists()) return;
        Document doc = UtilidadesXML.xmlToDom(RUTA_XML);
        if (doc == null) return;

        Element raiz = doc.getDocumentElement();
        NodeList listaNodos = raiz.getElementsByTagName("Matricula");

        for (int i = 0; i < listaNodos.getLength(); i++) {
            Node nodo = listaNodos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element matriculaDOM = (Element) nodo;
                try {
                    Matricula matricula = elementToMatricula(matriculaDOM);
                    if (matricula != null) {
                        coleccionMatriculas.add(matricula);
                    }
                } catch (Exception e) {
                    // Muestra un mensaje (puedes poner un logger si quieres)
                    System.err.println("Matrícula saltada por datos inválidos (id: "
                            + matriculaDOM.getAttribute("Id") + "): " + e.getMessage());
                    // NO relances la excepción, así simplemente ignora la matrícula inválida
                }
            }
        }
    }

    private void escribirXML() {
        Document domVacio = UtilidadesXML.crearDomVacio("Matriculas");
        Element raiz = domVacio.getDocumentElement();
        for (Matricula matricula : coleccionMatriculas) {
            Element eMatricula = matriculaToElement(domVacio, matricula);
            raiz.appendChild(eMatricula);
        }
        UtilidadesXML.domToXml(domVacio, RUTA_XML);
    }
    private Matricula elementToMatricula(Element matriculaDOM) throws OperationNotSupportedException {
        String idMatriculaStr = matriculaDOM.getAttribute("Id");
        int idMatricula = Integer.parseInt(idMatriculaStr);

        String dniAlumno = matriculaDOM.getAttribute("Alumno");
        Alumno alumno = null;
        for (Alumno a : Alumnos.getInstancia().get()) {
            if (a.getDni().equals(dniAlumno)) {
                alumno = a;
                break;
            }
        }
        if (alumno == null) {
            throw new IllegalArgumentException("ERROR: Alumno con DNI " + dniAlumno + " no encontrado.");
        }

        String cursoAcademico = ((Element) matriculaDOM.getElementsByTagName("Curso").item(0)).getTextContent();
        String fechaMatriculacionStr = ((Element) matriculaDOM.getElementsByTagName("FechaMatriculacion").item(0)).getTextContent();
        LocalDate fechaMatriculacion = LocalDate.parse(fechaMatriculacionStr, DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA));

        LocalDate fechaAnulacion = null;
        NodeList listaAnulacion = matriculaDOM.getElementsByTagName("FechaAnulacion");
        if (listaAnulacion.getLength() > 0) {
            String fechaAnulacionStr = ((Element) listaAnulacion.item(0)).getTextContent();
            fechaAnulacion = LocalDate.parse(fechaAnulacionStr, DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA));
        }

        // Asignaturas
        List<Asignatura> asignaturasMatricula = new ArrayList<>();
        Element eAsignaturas = (Element) matriculaDOM.getElementsByTagName("Asignaturas").item(0);
        NodeList listaAsignaturas = eAsignaturas.getElementsByTagName("Asignatura");
        for (int i = 0; i < listaAsignaturas.getLength(); i++) {
            Element asignaturaDOM = (Element) listaAsignaturas.item(i);
            String codigoAsignatura = asignaturaDOM.getAttribute("Codigo");
            Asignatura asignatura = null;
            for (Asignatura a : Asignaturas.getInstancia().get()) {
                if (a.getCodigo().equals(codigoAsignatura)) {
                    asignatura = a;
                    break;
                }
            }
            if (asignatura != null) {
                asignaturasMatricula.add(asignatura);
            } else {
                throw new IllegalArgumentException("ERROR: Asignatura con código " + codigoAsignatura + " no encontrada.");
            }
        }

        Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno, asignaturasMatricula);
        if (fechaAnulacion != null) {
            matricula.setFechaAnulacion(fechaAnulacion);
        }
        return matricula;
    }
    private Element matriculaToElement(Document doc, Matricula matricula) {
        Element eMatricula = doc.createElement("Matricula");
        eMatricula.setAttribute("Id", String.valueOf(matricula.getIdMatricula()));
        eMatricula.setAttribute("Alumno", matricula.getAlumno().getDni());

        Element eCurso = doc.createElement("Curso");
        eCurso.setTextContent(matricula.getCursoAcademico());
        eMatricula.appendChild(eCurso);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Matricula.FORMATO_FECHA);

        Element eFechaMatriculacion = doc.createElement("FechaMatriculacion");
        eFechaMatriculacion.setTextContent(matricula.getFechaMatriculacion().format(formatter));
        eMatricula.appendChild(eFechaMatriculacion);

        if (matricula.getFechaAnulacion() != null) {
            Element eFechaAnulacion = doc.createElement("FechaAnulacion");
            eFechaAnulacion.setTextContent(matricula.getFechaAnulacion().format(formatter));
            eMatricula.appendChild(eFechaAnulacion);
        }

        Element eAsignaturas = doc.createElement("Asignaturas");
        for (Asignatura a : matricula.getColeccionAsignaturas()) {
            Element eAsignatura = doc.createElement("Asignatura");
            eAsignatura.setAttribute("Codigo", a.getCodigo());
            eAsignaturas.appendChild(eAsignatura);
        }
        eMatricula.appendChild(eAsignaturas);

        return eMatricula;
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
        leerXML();
    }

    @Override
    public void terminar() {
        escribirXML();
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
