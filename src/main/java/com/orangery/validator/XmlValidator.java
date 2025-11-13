package com.orangery.validator;

import com.orangery.util.ResourceUtil;
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

    public boolean validate(String xmlName, String xsdName) {
        try {
            File xml = ResourceUtil.getResourceFile(xmlName);
            File xsd = ResourceUtil.getResourceFile(xsdName);

            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = factory.newSchema(xsd);
            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(xml));

            logger.info("XML validation SUCCESS: {}", xmlName);
            return true;

        } catch (Exception e) {
            logger.error("XML validation FAILED: {} → {}", xmlName, e.getMessage());
            return false;
        }
    }
}