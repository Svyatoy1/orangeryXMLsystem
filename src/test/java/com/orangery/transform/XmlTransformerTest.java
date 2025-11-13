package com.orangery.transform;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class XmlTransformerTest {

    @Test
    void testTransformation() {
        XmlTransformer transformer = new XmlTransformer();

        String input = "src/main/resources/flowers.xml";
        String xsl = "src/main/resources/transform.xml";
        String output = "src/test/resources/output.xml";

        boolean result = transformer.transform(input, xsl, output);

        assertTrue(result, "Transformation must succeed");
        assertTrue(new File(output).exists(), "Output file must be generated");
    }
}