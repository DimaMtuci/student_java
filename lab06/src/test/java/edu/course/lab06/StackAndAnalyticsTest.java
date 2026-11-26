package edu.course.lab06;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class StackAndAnalyticsTest {

    @Test
    void testStackWithStringsAndExceptions() {
        Stack<String> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());

        
        assertThrows(NoSuchElementException.class, stack::pop);
        assertThrows(NoSuchElementException.class, stack::peek);

        
        assertThrows(IllegalArgumentException.class, () -> stack.push(null));

        
        stack.push("First");
        stack.push("Second");
        assertFalse(stack.isEmpty());
        assertEquals(2, stack.size());
        assertEquals("Second", stack.peek());
        assertEquals("Second", stack.pop());
        assertEquals("First", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testStackWithDataSamples() {
        Stack<DataSample> sampleStack = new Stack<>();
        DataSample s1 = new DataSample("S-1", "cat", "cam-1", 10, SampleStatus.READY);
        DataSample s2 = new DataSample("S-2", "dog", "cam-2", 20, SampleStatus.FAILED);

        sampleStack.push(s1);
        sampleStack.push(s2);

        assertEquals(2, sampleStack.size());
        assertEquals("S-2", sampleStack.peek().getId());
        assertEquals("S-2", sampleStack.pop().getId());
        assertEquals("S-1", sampleStack.pop().getId());
    }

    @Test
    void testSampleAnalytics() {
        DataSample s1 = new DataSample("S-02", "cat", "cam-1", 128, SampleStatus.READY);
        DataSample s2 = new DataSample("S-01", "dog", "cam-2", 128, SampleStatus.READY);
        DataSample s3 = new DataSample("S-03", "bird", "cam-1", 64, SampleStatus.FAILED);

        List<DataSample> originalList = List.of(s1, s2, s3);
        SampleAnalytics analytics = new SampleAnalytics();

     
        Map<SampleStatus, Integer> counts = analytics.countByStatus(originalList);
        assertEquals(2, counts.get(SampleStatus.READY));
        assertEquals(1, counts.get(SampleStatus.FAILED));
        assertNull(counts.get(SampleStatus.PROCESSING));

       
        Set<String> sources = analytics.getUniqueSources(originalList);
        assertEquals(2, sources.size());
        assertTrue(sources.contains("cam-1"));
        assertTrue(sources.contains("cam-2"));

        List<DataSample> sorted = analytics.sortByFeatureCountAndId(originalList);
        assertEquals("S-03", sorted.get(0).getId());
        assertEquals("S-01", sorted.get(1).getId());
        assertEquals("S-02", sorted.get(2).getId());

       
        assertEquals("S-02", originalList.get(0).getId());
    }
}
