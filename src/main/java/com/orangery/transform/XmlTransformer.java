package com.orangery.transform;

import com.orangery.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import java.io.StringWriter;
import java.io.StringReader;
import java.util.Objects;

import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;


public class XmlTransformer {

    private static final Logger logger = LoggerFactory.getLogger(XmlTransformer.class);

    public void transform(String xmlName, String xslName, String outputName) {
        try {
            File xml = ResourceUtil.getResourceFile(xmlName);
            File xsl = ResourceUtil.getResourceFile(xslName);

            File outFile = new File("src/main/resources/output/output.html");

            File parent = outFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsl));

            transformer.transform(new StreamSource(xml), new StreamResult(outFile));

            System.out.println("HTML updated: " + outFile.getAbsolutePath());

            // Auto-open in browser (Windows)
            try {
                Runtime.getRuntime().exec("cmd /c start " + outFile.getAbsolutePath());
            } catch (Exception ex) {
                logger.error("Cannot open output file automatically: " + ex.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException("XSL transformation failed: " + e.getMessage());
        }
    }

    public String transformStringXml(String xmlString, String xslName) {
        try {
            StringReader reader = new StringReader(xmlString);

            InputStream xsl = getClass().getClassLoader().getResourceAsStream(xslName);
            Objects.requireNonNull(xsl, "XSL not found: " + xslName);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsl));

            StringWriter writer = new StringWriter();
            transformer.transform(new StreamSource(reader), new StreamResult(writer));

            return writer.toString();

        } catch (Exception e) {
            throw new RuntimeException("XSL transformation failed: " + e.getMessage());
        }
    }
}