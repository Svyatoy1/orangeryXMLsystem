package com.orangery.transform;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XmlTransformer {

    /**
     * XSLT-transformation
     * @param xmlPath path to input XML
     * @param xslPath path to XSL-файлу
     * @param outputPath path to output XML
     * @return true — if transformation is successful
     */
    public boolean transform(String xmlPath, String xslPath, String outputPath) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();

            StreamSource xsl = new StreamSource(new File(xslPath));
            StreamSource xml = new StreamSource(new File(xmlPath));

            Transformer transformer = factory.newTransformer(xsl);

            StreamResult result = new StreamResult(new File(outputPath));

            transformer.transform(xml, result);

            return true;
        } catch (Exception e) {
            System.out.println("XSLT error: " + e.getMessage());
            return false;
        }
    }
}