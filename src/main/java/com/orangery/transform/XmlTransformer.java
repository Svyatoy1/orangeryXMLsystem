package com.orangery.transform;

import com.orangery.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlTransformer {

    private static final Logger logger = LoggerFactory.getLogger(XmlTransformer.class);

    public void transform(String xmlName, String xslName, String outputPath) {
        try {
            File xml = ResourceUtil.getResourceFile(xmlName);
            File xsl = ResourceUtil.getResourceFile(xslName);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsl));

            transformer.transform(new StreamSource(xml), new StreamResult(new File(outputPath)));

            logger.info("Transformation successful → {}", outputPath);

        } catch (Exception e) {
            logger.error("XSL transformation FAILED: {}", e.getMessage());
            throw new RuntimeException("XSL transformation failed: " + e.getMessage());
        }
    }
}