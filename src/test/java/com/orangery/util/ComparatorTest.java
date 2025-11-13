package com.orangery.util;

import com.orangery.model.Flower;
import com.orangery.model.VisualParameters;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComparatorTest {

    @Test
    void testBySize() {
        Flower a = new Flower();
        Flower b = new Flower();
        Flower c = new Flower();

        VisualParameters vpA = new VisualParameters();
        vpA.setAverageSize(25);
        a.setVisualParameters(vpA);

        VisualParameters vpB = new VisualParameters();
        vpB.setAverageSize(30);
        b.setVisualParameters(vpB);

        VisualParameters vpC = new VisualParameters();
        vpC.setAverageSize(40);
        c.setVisualParameters(vpC);

        List<Flower> list = Arrays.asList(c, b, a);

        list.sort(new FlowerComparatorBySize());

        assertEquals(25, list.get(0).getVisualParameters().getAverageSize());
        assertEquals(30, list.get(1).getVisualParameters().getAverageSize());
        assertEquals(40, list.get(2).getVisualParameters().getAverageSize());
    }

    @Test
    void testByName() {
        Flower a = new Flower();
        a.setName("A");

        Flower b = new Flower();
        b.setName("C");

        Flower c = new Flower();
        c.setName("B");

        List<Flower> list = Arrays.asList(b, c, a);

        list.sort(new FlowerComparatorByName());

        assertEquals("A", list.get(0).getName());
        assertEquals("B", list.get(1).getName());
        assertEquals("C", list.get(2).getName());
    }

    @Test
    void testByOrigin() {
        Flower a = new Flower();
        a.setOrigin("Netherlands");

        Flower b = new Flower();
        b.setOrigin("Ecuador");

        Flower c = new Flower();
        c.setOrigin("Turkey");

        List<Flower> list = Arrays.asList(a, c, b);

        list.sort(new FlowerComparatorByOrigin());

        assertEquals("Ecuador", list.get(0).getOrigin());
        assertEquals("Netherlands", list.get(1).getOrigin());
        assertEquals("Turkey", list.get(2).getOrigin());
    }
}