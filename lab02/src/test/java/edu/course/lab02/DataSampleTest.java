package edu.course.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DataSampleTest {

    @Test
    void testValidDataSampleCreation() {
        double[] initialFeatures = {1.0, 2.0, 3.0};
        SampleId sampleId = new SampleId("id-1");
        DataSample sample = new DataSample(sampleId, "Cat", SampleStatus.CREATED, initialFeatures);

        assertEquals(sampleId, sample.getId());
        assertEquals("Cat", sample.getLabel());
        assertEquals(2.0, sample.getAverageFeature());
    }

    @Test
    void testNormalizationDoesNotChangeOriginalFeatures() {
        double[] initialFeatures = {10.0, 20.0, 50.0};
        DataSample sample = new DataSample(new SampleId("id-2"), "Data", SampleStatus.CREATED, initialFeatures);

        double[] normalized = sample.getNormalizedFeatures();
        
        // Проверяем, что нормализация посчиталась верно (от 0.0 до 1.0)
        assertEquals(0.0, normalized[0]);
        assertEquals(1.0, normalized[2]);
        
        // Проверяем инвариант: оригинальный массив внутри класса НЕ изменился!
        assertEquals(10.0, sample.getFeatures()[0]);
    }
}