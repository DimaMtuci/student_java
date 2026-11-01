package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

class SampleRegistryTest {

    private static class CollidingKey {
        private final String datasetName;
        private final int observationNumber;

        public CollidingKey(String datasetName, int observationNumber) {
            this.datasetName = datasetName;
            this.observationNumber = observationNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CollidingKey that = (CollidingKey) o;
            return observationNumber == that.observationNumber && datasetName.equals(that.datasetName);
        }

        @Override
        public int hashCode() {
            return 999; 
        }
    }

    @Test
    void testLogicalEqualityKeyLookup() {
        SampleRegistry registry = new SampleRegistry();
        SampleId id1 = new SampleId("IrisDataset", 42);
        DataSample sample = new DataSample(new double[]{5.1, 3.5}, "Iris-setosa");

        registry.put(id1, sample);

        SampleId id2 = new SampleId("IrisDataset", 42);

        assertNotNull(registry.get(id2));
        assertEquals("Iris-setosa", registry.get(id2).getLabel());
    }

    @Test
    void testDuplicateKeyReplacesValueWithoutSizeIncrease() {
        SampleRegistry registry = new SampleRegistry();
        SampleId id1 = new SampleId("MNIST", 1);
        DataSample sample1 = new DataSample(new double[]{0.0, 0.1}, "Digit 1");
        DataSample sample2 = new DataSample(new double[]{0.0, 0.9}, "Digit 7");

        registry.put(id1, sample1);
        assertEquals(1, registry.size());

        SampleId id2 = new SampleId("MNIST", 1);
        registry.put(id2, sample2);

        assertEquals(1, registry.size());
        assertEquals("Digit 7", registry.get(id1).getLabel());
    }

    @Test
    void testCollisionHandlingWithSameHashCode() {
        // Тестируем логику коллизий внутри HashMap напрямую с нашим кастомным ключом
        Map<CollidingKey, DataSample> collisionMap = new HashMap<>();
        
        CollidingKey key1 = new CollidingKey("CollisionTest", 10);
        CollidingKey key2 = new CollidingKey("CollisionTest", 20);

        DataSample sample1 = new DataSample(new double[]{1.0}, "Sample A");
        DataSample sample2 = new DataSample(new double[]{2.0}, "Sample B");

        collisionMap.put(key1, sample1);
        collisionMap.put(key2, sample2);

        assertEquals(2, collisionMap.size());
        assertEquals("Sample A", collisionMap.get(key1).getLabel());
        assertEquals("Sample B", collisionMap.get(key2).getLabel());
    }

    @Test
    void testEqualsWithNullAndDifferentType() {
        SampleId id = new SampleId("Test", 1);
        assertNotEquals(null, id);
        assertNotEquals("string type", id);
    }

    @Test
    void testMutableKeyProblemExperiment() {
        Map<MutableSampleId, String> badMap = new HashMap<>();
        MutableSampleId mutableKey = new MutableSampleId("DynamicSet", 5);
        
        badMap.put(mutableKey, "Important AI Weights");
        assertEquals("Important AI Weights", badMap.get(mutableKey));

        mutableKey.setDatasetName("HackedSet");

        assertNull(badMap.get(mutableKey));
    }
}
