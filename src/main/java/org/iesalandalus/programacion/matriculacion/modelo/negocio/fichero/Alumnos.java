package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
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

public class Alumnos implements IAlumnos {

    private static Alumnos instancia;
    private static final String RUTA_XML = "datos/alumnos.xml";
    private static List <Alumno> coleccionAlumnos;

    private Alumnos() {
        coleccionAlumnos = new ArrayList<>();
    }

    public static Alumnos getInstancia() {
        if (instancia == null) {
            instancia = new Alumnos();
        }
        return instancia;
    }


    @Override
    public void comenzar(){
        leerXML();
    }

    @Override
    public void terminar(){
        escribirXML();
    }

    private void leerXML() {
        coleccionAlumnos.clear();
        File fichero = new File(RUTA_XML);
        if (!fichero.exists()) {
            return;
        }
        Document doc = UtilidadesXML.xmlToDom(RUTA_XML);
        if (doc == null) return;

        Element raiz = doc.getDocumentElement();
        NodeList listaNodos = raiz.getElementsByTagName("Alumno");

        for (int i = 0; i < listaNodos.getLength(); i++) {
            Node nodo = listaNodos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element alumnoDOM = (Element) nodo;
                Alumno alumno = elementToAlumno(alumnoDOM);
                if (alumno != null) {
                    coleccionAlumnos.add(alumno);
                }
            }
        }
    }
    private void escribirXML() {
        Document domVacio = UtilidadesXML.crearDomVacio("Alumnos");
        Element raiz = domVacio.getDocumentElement();

        for (Alumno alumno : coleccionAlumnos) {
            Element eAlumno = alumnoToElement(domVacio, alumno);
            raiz.appendChild(eAlumno);
        }

        UtilidadesXML.domToXml(domVacio, RUTA_XML);
    }

    private Alumno elementToAlumno(Element alumnoDOM) {
        String dni = alumnoDOM.getAttribute("Dni");
        String nombre = ((Element) alumnoDOM.getElementsByTagName("Nombre").item(0)).getTextContent();
        String telefono = ((Element) alumnoDOM.getElementsByTagName("Telefono").item(0)).getTextContent();
        String correo = ((Element) alumnoDOM.getElementsByTagName("Correo").item(0)).getTextContent();
        String fechaNacimientoStr = ((Element) alumnoDOM.getElementsByTagName("FechaNacimiento").item(0)).getTextContent();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, formatter);

        return new Alumno(nombre, dni, correo, telefono, fechaNacimiento);
    }

    // Método alumnoToElement: convierte un objeto Alumno en un Element XML
    private Element alumnoToElement(Document doc, Alumno alumno) {
        Element eAlumno = doc.createElement("Alumno");
        eAlumno.setAttribute("Dni", alumno.getDni());

        Element eNombre = doc.createElement("Nombre");
        eNombre.setTextContent(alumno.getNombre());
        eAlumno.appendChild(eNombre);

        Element eTelefono = doc.createElement("Telefono");
        eTelefono.setTextContent(alumno.getTelefono());
        eAlumno.appendChild(eTelefono);

        Element eCorreo = doc.createElement("Correo");
        eCorreo.setTextContent(alumno.getCorreo());
        eAlumno.appendChild(eCorreo);

        Element eFechaNacimiento = doc.createElement("FechaNacimiento");
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        eFechaNacimiento.setTextContent(alumno.getFechaNacimiento().format(formatter));
        eAlumno.appendChild(eFechaNacimiento);

        return eAlumno;
    }




    private List<Alumno> copiaProfundaAlumnos(){

        List <Alumno> copiaAlumno = new ArrayList<>();

        for (Alumno alumno : coleccionAlumnos){
            copiaAlumno.add(new Alumno(alumno));
        }


        return copiaAlumno;
    }

    public List<Alumno> get (){

        List<Alumno> copia = copiaProfundaAlumnos();

        if (copia.isEmpty()){
            throw new IllegalArgumentException("ERROR: No hay alumnos matriculados.");
        }

        return copia;
    }

    @Override
    public int getTamano() {
        return 0;
    }


    @Override
    public void insertar (Alumno alumno){

        if (!coleccionAlumnos.contains(alumno)){
            coleccionAlumnos.add(alumno);
        }else {
            throw new IllegalArgumentException("ERROR:El alumno ya existe.");
        }

    }
    @Override
    public Alumno buscar (Alumno alumno){
        Iterator <Alumno> alumnoIterator=coleccionAlumnos.iterator();

        while (alumnoIterator.hasNext()){
            Alumno alumno1 = alumnoIterator.next();

            if (alumno1.equals(alumno)){
                return alumno1;
            }

        }

        return null;
    }
    @Override
    public void borrar(Alumno alumno)throws OperationNotSupportedException{

        if (alumno==null){
            throw new NullPointerException("ERROR:El alumno no puede ser nulo.");
        }

        if (coleccionAlumnos.contains(alumno)){
            coleccionAlumnos.remove(alumno);
        }else
            throw new OperationNotSupportedException("ERROR:El alumno no se encuentra en la coleccion.");
    }







}
