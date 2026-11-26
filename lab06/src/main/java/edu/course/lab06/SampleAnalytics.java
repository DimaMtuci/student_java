package edu.course.lab06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SampleAnalytics {

    public Map<SampleStatus, Integer> countByStatus(List<DataSample> samples) {
        Map<SampleStatus, Integer> counts = new HashMap<>();
        for (DataSample sample : samples) {
            SampleStatus status = sample.getStatus();
            counts.put(status, counts.getOrDefault(status, 0) + 1);
        }
        return counts;
    }

    public Set<String> getUniqueSources(List<DataSample> samples) {
        Set<String> sources = new HashSet<>();
        for (DataSample sample : samples) {
            sources.add(sample.getSource());
        }
        return sources;
    }

    public List<DataSample> sortByFeatureCountAndId(List<DataSample> samples) {
        // Копируем список, чтобы не изменять исходный
        List<DataSample> sorted = new ArrayList<>(samples);
        sorted.sort(
            Comparator.comparingInt(DataSample::getFeatureCount)
                      .thenComparing(DataSample::getId)
        );
        return sorted;
    }
}
