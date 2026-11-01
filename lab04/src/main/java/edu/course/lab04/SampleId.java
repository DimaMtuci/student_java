package edu.course.lab04;

import java.util.Objects;

public final class SampleId {
    private final String datasetName;
    private final int observationNumber;
    public SampleId(String datasetName, int observationNumber) {
        if (datasetName == null) {
            throw new IllegalArgumentException("Имя набора данных не может быть null");
        }
        this.datasetName = datasetName;
        this.observationNumber = observationNumber;
    }

    public String getDatasetName() {
        return datasetName;
    }
    public int getObservationNumber() {
        return observationNumber;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SampleId sampleId = (SampleId) o;
        return observationNumber == sampleId.observationNumber && datasetName.equals(sampleId.datasetName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(datasetName, observationNumber);
    }
}
