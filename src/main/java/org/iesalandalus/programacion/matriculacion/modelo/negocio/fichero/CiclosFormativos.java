package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
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

public class CiclosFormativos implements ICiclosFormativos {

    private static CiclosFormativos instancia;
    List <CicloFormativo> coleccionCiclos;
    private static final String RUTA_XML = "datos/ciclos.xml";



    private CiclosFormativos() {
        coleccionCiclos = new ArrayList<>();
    }

    public static CiclosFormativos getInstancia() {
        if (instancia == null) {
            instancia = new CiclosFormativos();
        }
        return instancia;
    }
    private void leerXML() {
        coleccionCiclos.clear();
        File fichero = new File(RUTA_XML);
        if (!fichero.exists()) {
            return;
        }
        Document doc = UtilidadesXML.xmlToDom(RUTA_XML);
        if (doc == null) return;

        Element raiz = doc.getDocumentElement();
        NodeList listaNodos = raiz.getElementsByTagName("CicloFormativo");

        for (int i = 0; i < listaNodos.getLength(); i++) {
            Node nodo = listaNodos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element cicloDOM = (Element) nodo;
                CicloFormativo ciclo = elementToCicloFormativo(cicloDOM);
                if (ciclo != null) {
                    coleccionCiclos.add(ciclo);
                }
            }
        }
    }

    private void escribirXML() {
        Document domVacio = UtilidadesXML.crearDomVacio("CiclosFormativos");
        Element raiz = domVacio.getDocumentElement();

        for (CicloFormativo ciclo : coleccionCiclos) {
            Element eCiclo = cicloFormativoToElement(domVacio, ciclo);
            raiz.appendChild(eCiclo);
        }

        UtilidadesXML.domToXml(domVacio, RUTA_XML);
    }

    private CicloFormativo elementToCicloFormativo(Element cicloDOM) {
        int codigo = Integer.parseInt(cicloDOM.getAttribute("Codigo"));
        String nombre = ((Element) cicloDOM.getElementsByTagName("Nombre").item(0)).getTextContent();
        String familiaProfesional = ((Element) cicloDOM.getElementsByTagName("FamiliaProfesional").item(0)).getTextContent();
        int horas = Integer.parseInt(((Element) cicloDOM.getElementsByTagName("Horas").item(0)).getTextContent());

        // Grado
        Element gradoDOM = (Element) cicloDOM.getElementsByTagName("Grado").item(0);
        String tipoGrado = gradoDOM.getAttribute("Tipo");
        Grado grado = null;

        if ("GradoD".equals(tipoGrado)) {
            String nombreGrado = ((Element) gradoDOM.getElementsByTagName("Nombre").item(0)).getTextContent();
            int numAnios = Integer.parseInt(((Element) gradoDOM.getElementsByTagName("NumAnios").item(0)).getTextContent());
            String modalidadStr = ((Element) gradoDOM.getElementsByTagName("Modalidad").item(0)).getTextContent();

            if (modalidadStr.contains("cadenaAMostrar='")) {
                modalidadStr = modalidadStr.substring(modalidadStr.indexOf("'") + 1, modalidadStr.lastIndexOf("'"));
            }
            Modalidad modalidad = Modalidad.valueOf(modalidadStr);
            grado = new GradoD(nombreGrado, numAnios, modalidad);
        } else if ("GradoE".equals(tipoGrado)) {
            String nombreGrado = ((Element) gradoDOM.getElementsByTagName("Nombre").item(0)).getTextContent();
            int numAnios = Integer.parseInt(((Element) gradoDOM.getElementsByTagName("NumAnios").item(0)).getTextContent());
            int numEdiciones = Integer.parseInt(((Element) gradoDOM.getElementsByTagName("NumEdiciones").item(0)).getTextContent());
            grado = new GradoE(nombreGrado, numAnios, numEdiciones);
        }



        // Orden: int codigo, String familiaProfesional, Grado grado, String nombre, int horas
        return new CicloFormativo(codigo, familiaProfesional, grado, nombre, horas);
    }

    private Element cicloFormativoToElement(Document doc, CicloFormativo ciclo) {
        Element eCiclo = doc.createElement("CicloFormativo");
        eCiclo.setAttribute("Codigo", String.valueOf(ciclo.getCodigo()));

        Element eNombre = doc.createElement("Nombre");
        eNombre.setTextContent(ciclo.getNombre());
        eCiclo.appendChild(eNombre);

        Element eFamilia = doc.createElement("FamiliaProfesional");
        eFamilia.setTextContent(ciclo.getFamiliaProfesional());
        eCiclo.appendChild(eFamilia);

        Element eHoras = doc.createElement("Horas");
        eHoras.setTextContent(String.valueOf(ciclo.getHoras()));
        eCiclo.appendChild(eHoras);

        // Grado
        Grado grado = ciclo.getGrado();
        Element eGrado = doc.createElement("Grado");

        if (grado instanceof GradoD) {
            GradoD gradoD = (GradoD) grado;
            eGrado.setAttribute("Tipo", "GradoD");
            Element eNombreGrado = doc.createElement("Nombre");
            eNombreGrado.setTextContent(gradoD.getNombre());
            eGrado.appendChild(eNombreGrado);
            Element eNumAnios = doc.createElement("NumAnios");
            eNumAnios.setTextContent(String.valueOf(gradoD.getNumAnios()));
            eGrado.appendChild(eNumAnios);
            Element eModalidad = doc.createElement("Modalidad");
            eModalidad.setTextContent(gradoD.getModalidad().toString());
            eGrado.appendChild(eModalidad);
        } else if (grado instanceof GradoE) {
            GradoE gradoE = (GradoE) grado;
            eGrado.setAttribute("Tipo", "GradoE");
            Element eNombreGrado = doc.createElement("Nombre");
            eNombreGrado.setTextContent(gradoE.getNombre());
            eGrado.appendChild(eNombreGrado);
            Element eNumAnios = doc.createElement("NumAnios");
            eNumAnios.setTextContent(String.valueOf(gradoE.getNumAnios()));
            eGrado.appendChild(eNumAnios);
            Element eNumEdiciones = doc.createElement("NumEdiciones");
            eNumEdiciones.setTextContent(String.valueOf(gradoE.getNumEdiciones()));
            eGrado.appendChild(eNumEdiciones);
        }

        eCiclo.appendChild(eGrado);
        return eCiclo;
    }

    private List<CicloFormativo> copiaProfundaCiclosFormativos(){

        List<CicloFormativo> copiaCiclo =new ArrayList<>();

        for (CicloFormativo cicloFormativo : coleccionCiclos){
            copiaCiclo.add(new CicloFormativo(cicloFormativo)); //Usamos el constructor copia
        }
        return copiaCiclo;
    }

    @Override
    public void comenzar() {
        leerXML();
    }

    @Override
    public void terminar() {
        escribirXML();
    }

    public List<CicloFormativo> get (){

       List<CicloFormativo> copiaCic = copiaProfundaCiclosFormativos();
        if (copiaCic.isEmpty()){
            throw new IllegalArgumentException("ERROR: No hay ciclos matriculados.");
        }

        return copiaCic;
    }

    @Override
    public int getTamano() {
        return 0;
    }

    public void insertar (CicloFormativo cicloFormativo)throws OperationNotSupportedException{

        if (!coleccionCiclos.contains(cicloFormativo)) {
        coleccionCiclos.add(cicloFormativo);
        }else {
            System.out.println("ERROR:El ciclo formativo ya existe.");
        }
    }

    public CicloFormativo buscar (CicloFormativo cicloFormativo){

        Iterator<CicloFormativo> cicloFormativoIterator = coleccionCiclos.iterator();

        while (cicloFormativoIterator.hasNext()){
            CicloFormativo cicloFormativo1 = cicloFormativoIterator.next();

            if (cicloFormativo1.equals(cicloFormativo)){
                return cicloFormativo1;
            }
        }
        return null;
    }

    public void borrar (CicloFormativo cicloFormativo)throws OperationNotSupportedException{

        if (coleccionCiclos.contains(cicloFormativo)){
            coleccionCiclos.remove(cicloFormativo);
        }else {
            throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");

        }
    }


}
