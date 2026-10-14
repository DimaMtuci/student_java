package edu.course.lab02;

import java.util.Arrays;

public class DataSample {
    private final SampleId id; 
    private final String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(SampleId id, String label, SampleStatus status, double[] features) {
        if (id == null) {
            throw new IllegalArgumentException("Идентификатор не может быть null");
        }
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Текстовая метка не может быть пустой");
        }
        if (status == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Массив признаков должен содержать хотя бы один элемент");
        }

        this.id = id;
        this.label = label;
        this.status = status;
        this.features = Arrays.copyOf(features, features.length);
    }

    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Новый статус не может быть null");
        }
        this.status = newStatus;
    }

    public boolean isReady() {
        return this.status == SampleStatus.READY;
    }

    public double getAverageFeature() {
        double sum = 0;
        for (double f : features) {
            sum += f;
        }
        return sum / features.length;
    }


    public double[] getNormalizedFeatures() {
        double min = features[0];
        double max = features[0];
        for (double f : features) {
            if (f < min) min = f;
            if (f > max) max = f;
        }
        
        double[] normalized = new double[features.length];
        double range = max - min;
        
        for (int i = 0; i < features.length; i++) {
            if (range == 0) {
                normalized[i] = 0.0;
            } else {
                normalized[i] = (features[i] - min) / range;
            }
        }
        return normalized;
    }

    public SampleId getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public SampleStatus getStatus() {
        return status;
    }

    public double[] getFeatures() {
        return Arrays.copyOf(features, features.length);
    }
}