package com.orangery.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XmlValidator {

    private static final Logger logger = LoggerFactory.getLogger(XmlValidator.class);

    public boolean validate(String xmlPath, String xsdPath) {
        try {
            logger.info("Starting XML validation: xml={}, xsd={}", xmlPath, xsdPath);

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(new File(xmlPath)));

            logger.info("XML validation SUCCESS: {}", xmlPath);
            return true;

        } catch (Exception e) {
            logger.error("XML validation FAILED: {} → {}", xmlPath, e.getMessage());
            return false;
        }
    }
}