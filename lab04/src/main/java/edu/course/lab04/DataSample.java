package edu.course.lab04;

import java.util.Arrays;

public class DataSample {
    private final double[] features;
    private final String label;

    public DataSample(double[] features, String label) {
        this.features = features != null ? features.clone() : new double[0];
        this.label = label;
    }

    public double[] getFeatures() {
        return features.clone();
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "DataSample{" + "features=" + Arrays.toString(features) + ", label='" + label + '\'' + '}';
    }
}
