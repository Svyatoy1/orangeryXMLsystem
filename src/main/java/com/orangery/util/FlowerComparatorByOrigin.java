package com.orangery.util;

import com.orangery.model.Flower;
import java.util.Comparator;

public class FlowerComparatorByOrigin implements Comparator<Flower> {
    @Override
    public int compare(Flower f1, Flower f2) {
        return f1.getOrigin().compareToIgnoreCase(f2.getOrigin());
    }
}