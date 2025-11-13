package com.orangery.parser;

import com.orangery.model.Flower;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomParserTest {

    @Test
    void testDomParser() {
        DomParser parser = new DomParser();
        List<Flower> flowers = parser.parse("src/main/resources/flowers.xml");

        assertNotNull(flowers);
        assertFalse(flowers.isEmpty());

        Flower f = flowers.get(0);

        assertNotNull(f.getName());
        assertNotNull(f.getSoil());
        assertNotNull(f.getOrigin());
        assertNotNull(f.getVisualParameters());
        assertNotNull(f.getGrowingTips());
        assertNotNull(f.getMultiplying());
    }
}