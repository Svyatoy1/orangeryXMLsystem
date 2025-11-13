package com.orangery.util;

import com.orangery.model.Flower;
import java.util.Comparator;

public class FlowerComparatorBySize implements Comparator<Flower> {
    @Override
    public int compare(Flower f1, Flower f2) {
        return Double.compare(
                f1.getVisualParameters().getAverageSize(),
                f2.getVisualParameters().getAverageSize()
        );
    }
}