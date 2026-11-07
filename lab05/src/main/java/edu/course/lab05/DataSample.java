package edu.course.lab05;

public final class DataSample {
    private final String id;
    private final String label;
    private final String source;
    private final int featureCount;
    private final SampleStatus status;

    public DataSample(String id, String label, String source, int featureCount, SampleStatus status) {
        this.id = id;
        this.label = label;
        this.source = source;
        this.featureCount = featureCount;
        this.status = status;
    }

    public String getId() { return id; }
    public String getLabel() { return label; }
    public String getSource() { return source; }
    public int getFeatureCount() { return featureCount; }
    public SampleStatus getStatus() { return status; }
}
