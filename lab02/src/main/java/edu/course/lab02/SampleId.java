package edu.course.lab02;

public record SampleId(String value) {
    public SampleId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Идентификатор не может быть пустым");
        }
    }
}