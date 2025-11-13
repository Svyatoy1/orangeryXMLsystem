package com.orangery.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.io.File;

public class XmlValidator {

    private static final Logger logger = LoggerFactory.getLogger(XmlValidator.class);

    public boolean validate(String xmlPath, String xsdPath) {
        logger.info("Validating XML '{}' with XSD '{}'", xmlPath, xsdPath);

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(new File(xmlPath)));

            logger.info("XML validation successful.");
            return true;

        } catch (Exception e) {
            logger.error("Validation failed: {}", e.getMessage());
            return false;
        }
    }
}