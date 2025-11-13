package com.orangery.parser;

import com.orangery.model.Flower;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StaxParserTest {

    @Test
    void testStaxParserParsesXmlCorrectly() {

        StaxParser parser = new StaxParser();
        String xmlPath = "src/main/resources/flowers.xml";

        List<Flower> flowers = parser.parse(xmlPath);

        assertNotNull(flowers);
        assertFalse(flowers.isEmpty());

        Flower f = flowers.get(0);

        assertNotNull(f.getName());
        assertNotNull(f.getOrigin());
        assertNotNull(f.getSoil());
        assertNotNull(f.getVisualParameters());
        assertNotNull(f.getGrowingTips());
        assertNotNull(f.getMultiplying());
    }
}