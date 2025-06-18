package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Curso;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.EspecialidadProfesorado;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Asignaturas implements IAsignaturas {

    private static List<Asignatura> coleccionAsignaturas;

    private static Asignaturas instancia;
    private static final String RUTA_XML = "datos/asignaturas.xml";

    private Asignaturas() {
        coleccionAsignaturas= new ArrayList<>();
    }
    public static Asignaturas getInstancia() {
        if (instancia == null) {
            instancia = new Asignaturas();
        }
        return instancia;
    }
    private void leerXML() {
        coleccionAsignaturas.clear();
        File fichero = new File(RUTA_XML);
        if (!fichero.exists()) return;
        Document doc = UtilidadesXML.xmlToDom(RUTA_XML);
        if (doc == null) return;

        Element raiz = doc.getDocumentElement();
        NodeList listaNodos = raiz.getElementsByTagName("Asignatura");

        for (int i = 0; i < listaNodos.getLength(); i++) {
            Node nodo = listaNodos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element asignaturaDOM = (Element) nodo;
                Asignatura asignatura = elementToAsignatura(asignaturaDOM);
                if (asignatura != null) {
                    coleccionAsignaturas.add(asignatura);
                }
            }
        }
    }
    private void escribirXML() {
        Document domVacio = UtilidadesXML.crearDomVacio("Asignaturas");
        Element raiz = domVacio.getDocumentElement();
        for (Asignatura asignatura : coleccionAsignaturas) {
            Element eAsignatura = asignaturaToElement(domVacio, asignatura);
            raiz.appendChild(eAsignatura);
        }
        UtilidadesXML.domToXml(domVacio, RUTA_XML);
    }

    private Asignatura elementToAsignatura(Element asignaturaDOM) {
        String codigo = asignaturaDOM.getAttribute("Codigo");
        String nombre = ((Element) asignaturaDOM.getElementsByTagName("Nombre").item(0)).getTextContent();
        String cursoStr = ((Element) asignaturaDOM.getElementsByTagName("Curso").item(0)).getTextContent();
        String especialidadStr = ((Element) asignaturaDOM.getElementsByTagName("EspecialidadProfesorado").item(0)).getTextContent();
        String cicloStr = ((Element) asignaturaDOM.getElementsByTagName("CicloFormativo").item(0)).getTextContent();

        // Horas
        Element horasDOM = (Element) asignaturaDOM.getElementsByTagName("Horas").item(0);
        int horasAnuales = Integer.parseInt(((Element) horasDOM.getElementsByTagName("Anuales").item(0)).getTextContent());
        int horasDesdoble = Integer.parseInt(((Element) horasDOM.getElementsByTagName("Desdoble").item(0)).getTextContent());

        // Buscar el ciclo formativo por código en CiclosFormativos
        int codigoCiclo = Integer.parseInt(cicloStr);
        CicloFormativo cicloFormativo = null;
        for (CicloFormativo c : CiclosFormativos.getInstancia().coleccionCiclos) {
            if (c.getCodigo() == codigoCiclo) {
                cicloFormativo = c;
                break;
            }
        }
        if (cicloFormativo == null) {
            throw new IllegalArgumentException("ERROR: Ciclo formativo con código " + cicloStr + " no encontrado.");
        }
        if (cursoStr.contains("cadenaAMostrar='")) {
            cursoStr = cursoStr.substring(cursoStr.indexOf("'") + 1, cursoStr.lastIndexOf("'"));
        }
        if (especialidadStr.contains("cadenaAMostrar='")) {
            especialidadStr = especialidadStr.substring(
                    especialidadStr.indexOf("'") + 1,
                    especialidadStr.lastIndexOf("'")
            );
        }

        Curso curso = Curso.valueOf(cursoStr);
        EspecialidadProfesorado especialidad = EspecialidadProfesorado.valueOf(especialidadStr);

        return new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidad, cicloFormativo);
    }
    private Element asignaturaToElement(Document doc, Asignatura asignatura) {
        Element eAsignatura = doc.createElement("Asignatura");
        eAsignatura.setAttribute("Codigo", asignatura.getCodigo());

        Element eNombre = doc.createElement("Nombre");
        eNombre.setTextContent(asignatura.getNombre());
        eAsignatura.appendChild(eNombre);

        Element eCurso = doc.createElement("Curso");
        eCurso.setTextContent(asignatura.getCurso().toString());
        eAsignatura.appendChild(eCurso);

        Element eEspecialidad = doc.createElement("EspecialidadProfesorado");
        eEspecialidad.setTextContent(asignatura.getEspecialidadProfesorado().toString());
        eAsignatura.appendChild(eEspecialidad);

        Element eCiclo = doc.createElement("CicloFormativo");
        eCiclo.setTextContent(String.valueOf(asignatura.getCicloFormativo().getCodigo()));
        eAsignatura.appendChild(eCiclo);

        Element eHoras = doc.createElement("Horas");
        Element eAnuales = doc.createElement("Anuales");
        eAnuales.setTextContent(String.valueOf(asignatura.getHorasAnuales()));
        eHoras.appendChild(eAnuales);
        Element eDesdoble = doc.createElement("Desdoble");
        eDesdoble.setTextContent(String.valueOf(asignatura.getHorasDesdoble()));
        eHoras.appendChild(eDesdoble);
        eAsignatura.appendChild(eHoras);

        return eAsignatura;
    }



    private List<Asignatura> copiaProfundaAsignaturas (){

        List <Asignatura> copiaAsignaturas = new ArrayList<>();

        for (Asignatura asignatura : coleccionAsignaturas){
            copiaAsignaturas.add(new Asignatura(asignatura));
        }

        return copiaAsignaturas;
    }

    @Override
    public void comenzar() {
        leerXML();
    }

    @Override
    public void terminar() {
        escribirXML();
    }

    public List<Asignatura> get (){

        List<Asignatura> copiaAs=copiaProfundaAsignaturas();

        if (copiaAs.isEmpty()){
            throw new IllegalArgumentException("ERROR:No hay asignaturas matriculadas, inserte una.");
        }


        return copiaAs;
    }

    @Override
    public int getTamano() {
        return 0;
    }

    public void insertar (Asignatura asignatura)throws OperationNotSupportedException{

        if (!coleccionAsignaturas.contains(asignatura)){
            coleccionAsignaturas.add(asignatura);
        }else
            System.out.println("ERROR:La asignatura ya existe.");


    }

    public Asignatura buscar (Asignatura asignatura){

        Iterator <Asignatura> asignaturaIterator=coleccionAsignaturas.iterator();

        while (asignaturaIterator.hasNext()){
            Asignatura asignatura1= asignaturaIterator.next();

            if (asignatura1.equals(asignatura)){
                return asignatura1;
            }
        }
        return null;
    }


    public void borrar (Asignatura asignatura)throws OperationNotSupportedException{

        if (asignatura==null){
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }


        if (coleccionAsignaturas.contains(asignatura)){
            coleccionAsignaturas.remove(asignatura);
        }else{
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");

        }

    }


}
