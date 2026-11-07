package edu.course.lab05;

import java.util.List;

public final class LoadResult {
    private final List<DataSample> samples;
    private final List<ValidationError> errors;

    public LoadResult(List<DataSample> samples, List<ValidationError> errors) {
        this.samples = List.copyOf(samples);
        this.errors = List.copyOf(errors);
    }

    public List<DataSample> getSamples() { return samples; }
    public List<ValidationError> getErrors() { return errors; }
}
