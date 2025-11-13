package com.orangery.parser;

import com.orangery.model.Flower;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomParserTest {

    @Test
    void testDomParserParsesXmlCorrectly() {

        DomParser parser = new DomParser();
        String xmlPath = "src/main/resources/flowers.xml";

        List<Flower> flowers = parser.parse(xmlPath);

        // Перевіряємо що список не порожній
        assertNotNull(flowers, "Parser returned null list");
        assertFalse(flowers.isEmpty(), "Parsed list is empty");

        // Далі — точкові перевірки першого елемента (для стабільності тестів)
        Flower f = flowers.get(0);

        assertNotNull(f.getName());
        assertNotNull(f.getOrigin());
        assertNotNull(f.getSoil());
        assertNotNull(f.getVisualParameters());
        assertNotNull(f.getGrowingTips());
        assertNotNull(f.getMultiplying());

        // Перевіряємо що XML реально правильно розібрано
        assertTrue(f.getAverageSize() > 0);
        assertTrue(f.getGrowingTips().getTemperature() != 0);
    }
}