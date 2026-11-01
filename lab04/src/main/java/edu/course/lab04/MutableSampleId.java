package edu.course.lab04;

import java.util.Objects;

public class MutableSampleId {
    private String datasetName;
    private int observationNumber;

    public MutableSampleId(String datasetName, int observationNumber) {
        this.datasetName = datasetName;
        this.observationNumber = observationNumber;
    }
    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }
    public void setObservationNumber(int observationNumber) {
        this.observationNumber = observationNumber;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableSampleId evils = (MutableSampleId) o;
        return observationNumber == evils.observationNumber && Objects.equals(datasetName, evils.datasetName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(datasetName, observationNumber);
    }
}
