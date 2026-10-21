package edu.course.lab03;

import java.util.Arrays;

public class MinMaxNormalization implements NormalizationStrategy {
    @Override
    public double[] normalize(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }
        if (values.length == 0) {
            return new double[0];
        }
        double min = values[0];
        double max = values[0];
        for (double v : values) {
            if (v < min) min = v;
            if (v > max) max = v;
        }

        double[] result = new double[values.length];
        
        if (max == min) {
            Arrays.fill(result, 0.0);
            return result;
        }
        for (int i = 0; i < values.length; i++) {
            result[i] = (values[i] - min) / (max - min);
        }
        return result;
    }
}
