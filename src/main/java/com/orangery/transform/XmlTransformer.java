package com.orangery.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XmlTransformer {

    private static final Logger logger = LoggerFactory.getLogger(XmlTransformer.class);

    public boolean transform(String xmlInput, String xsltFile, String outputFile) {
        try {
            logger.info("Starting XSL transformation: xml={}, xsl={}, out={}",
                    xmlInput, xsltFile, outputFile);

            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File(xsltFile));

            Transformer transformer = factory.newTransformer(xslt);

            transformer.transform(
                    new StreamSource(new File(xmlInput)),
                    new StreamResult(new File(outputFile))
            );

            logger.info("XSL transformation SUCCESS → {}", outputFile);
            return true;

        } catch (Exception e) {
            logger.error("XSL transformation FAILED: {}", e.getMessage());
            return false;
        }
    }
}