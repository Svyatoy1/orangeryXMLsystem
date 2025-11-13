package com.orangery.validator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class XmlValidatorTest {

    @Test
    void testValidXML() {
        XmlValidator validator = new XmlValidator();

        boolean result = validator.validate(
                "src/main/resources/flowers.xml",
                "src/main/resources/flowers.xsd"
        );

        assertTrue(result, "Valid XML must pass validation");
    }

    @Test
    void testInvalidXML() {
        XmlValidator validator = new XmlValidator();

        boolean result = validator.validate(
                "src/test/resources/broken.xml",
                "src/main/resources/flowers.xsd"
        );

        assertFalse(result, "Broken XML must NOT pass validation");
    }
}