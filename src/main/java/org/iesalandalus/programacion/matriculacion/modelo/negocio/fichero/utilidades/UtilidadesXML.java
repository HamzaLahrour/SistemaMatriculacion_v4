package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class UtilidadesXML {

    public static Document xmlToDom(String rutaXml) {
        Document doc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docbui = dbf.newDocumentBuilder();
            FileInputStream fis = new FileInputStream(new File(rutaXml));
            doc = docbui.parse(fis);
            fis.close();
        } catch (Exception ex) {
            System.out.println("Error al convertir XML a DOM: " + ex.getMessage());
        }
        return doc;
    }
    public static boolean domToXml(Document DOM, String rutaXml) {
        try {
            File f = new File(rutaXml);
            FileOutputStream fos = new FileOutputStream(f);

            DOMSource source = new DOMSource(DOM);
            StreamResult result = new StreamResult(new OutputStreamWriter(fos,"UTF-8"));

            TransformerFactory tFactory = TransformerFactory.newInstance();
            tFactory.setAttribute("indent-number", Integer.valueOf(4));
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

            fos.close();
            System.out.printf("Fichero %s escrito correctamente.%n", rutaXml);
            return true;
        } catch (TransformerException | java.io.IOException ex) {
            System.out.println("Error al transformar DOM a XML: " + ex.getMessage());
        }
        return false;
    }
    public static Document crearDomVacio(String etiquetaRaiz) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc = null ;
        try {
            db = dbf.newDocumentBuilder();
            doc = db.newDocument();
            Element elementoRaiz = doc.createElement(etiquetaRaiz);
            doc.appendChild(elementoRaiz);
            return doc;
        } catch (ParserConfigurationException ex) {
            System.out.println("Error al crear DOM vacío: " + ex.getMessage());
        }
        return doc;
    }





}
