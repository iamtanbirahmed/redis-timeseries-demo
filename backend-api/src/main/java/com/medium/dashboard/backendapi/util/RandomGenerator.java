package com.medium.dashboard.backendapi.util;

import java.util.Random;

public class RandomGenerator {

    public static double generateDoubleValue(double rangeMin, double rangeMax) {
        double random = new Random().nextDouble();
        return rangeMin + (rangeMax - rangeMin) * random;
    }
}
