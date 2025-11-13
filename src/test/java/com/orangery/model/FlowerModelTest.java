package com.orangery.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlowerModelTest {

    @Test
    void testFlowerBasicFields() {
        Flower flower = new Flower();

        flower.setName("Rose");
        flower.setOrigin("Netherlands");
        flower.setSoil(Soil.PODZOLIC);
        flower.setMultiplying(Multiplying.SEEDS);

        assertEquals("Rose", flower.getName());
        assertEquals("Netherlands", flower.getOrigin());
        assertEquals(Soil.PODZOLIC, flower.getSoil());
        assertEquals(Multiplying.SEEDS, flower.getMultiplying());
    }

    @Test
    void testVisualParameters() {
        VisualParameters vp = new VisualParameters();
        vp.setStemColor("Green");
        vp.setLeafColor("DarkGreen");
        vp.setAverageSize(35);

        assertEquals("Green", vp.getStemColor());
        assertEquals("DarkGreen", vp.getLeafColor());
        assertEquals(35, vp.getAverageSize());
    }

    @Test
    void testGrowingTips() {
        GrowingTips tips = new GrowingTips();
        tips.setTemperature(22);
        tips.setLight(true);
        tips.setWatering(120);

        assertEquals(22, tips.getTemperature());
        assertTrue(tips.isLight());
        assertEquals(120, tips.getWatering());
    }

    @Test
    void testFlowerWithNestedObjects() {
        Flower flower = new Flower();

        VisualParameters vp = new VisualParameters();
        vp.setStemColor("Red");
        vp.setLeafColor("Brown");
        vp.setAverageSize(40);

        GrowingTips tips = new GrowingTips();
        tips.setTemperature(18);
        tips.setLight(false);
        tips.setWatering(150);

        flower.setVisualParameters(vp);
        flower.setGrowingTips(tips);

        assertEquals(40, flower.getVisualParameters().getAverageSize());
        assertEquals(18, flower.getGrowingTips().getTemperature());
    }
}