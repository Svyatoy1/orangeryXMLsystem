package com.orangery.validator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class XmlValidatorTest {

    @Test
    void testValidXml() {
        boolean result = XmlValidator.validate(
                "src/main/resources/flowers.xml",
                "src/main/resources/flowers.xsd"
        );

        assertTrue(result);
    }

    @Test
    void testInvalidXml() {
        boolean result = XmlValidator.validate(
                "src/test/resources/broken.xml",
                "src/main/resources/flowers.xsd"
        );

        assertFalse(result);
    }
}