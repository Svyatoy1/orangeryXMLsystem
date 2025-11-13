package com.orangery.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XmlValidatorTest {

    private static final String XSD = "src/main/resources/orangery.xsd";
    private static final String VALID_XML = "src/main/resources/flowers.xml";
    private static final String INVALID_XML = "src/main/resources/broken_flowers.xml";

    @Test
    void testValidXml() {
        XmlValidator validator = new XmlValidator();
        assertTrue(validator.validate(VALID_XML, XSD));
    }

    @Test
    void testInvalidXml() {
        XmlValidator validator = new XmlValidator();
        assertFalse(validator.validate(INVALID_XML, XSD));
    }
}