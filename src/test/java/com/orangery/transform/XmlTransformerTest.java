package com.orangery.transform;

import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class XmlTransformerTest {

    @Test
    void testTransform() {
        XmlTransformer transformer = new XmlTransformer();

        boolean result = transformer.transform(
                "src/main/resources/flowers.xml",
                "src/main/resources/transform.xml",
                "target/groupedFlowers.xml"
        );

        assertTrue(result);
        assertTrue(new File("target/groupedFlowers.xml").exists());
    }
}