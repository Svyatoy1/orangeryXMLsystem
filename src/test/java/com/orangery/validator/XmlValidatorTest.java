package com.orangery.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XmlValidatorTest {

    private final XmlValidator validator = new XmlValidator();

    @Test
    void testValidXml() {
        String xml = "src/main/resources/flowers.xml";
        String xsd = "src/main/resources/flowers.xsd";

        boolean result = validator.validate(xml, xsd);

        assertTrue(result, "Valid XML must pass validation");
    }

    @Test
    void testInvalidXml() {
        String xml = "src/test/resources/broken.xml";
        String xsd = "src/main/resources/flowers.xsd";

        boolean result = validator.validate(xml, xsd);

        assertFalse(result, "Broken XML must NOT pass validation");
    }
}