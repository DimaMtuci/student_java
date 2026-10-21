package edu.course.lab03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NormalizationStrategyTest {

    @Test
    void testMinMaxNormalizationRegular() {
        NormalizationStrategy strategy = new MinMaxNormalization();
        double[] input = {1.0, 2.0, 3.0};
        double[] expected = {0.0, 0.5, 1.0};
        
        assertArrayEquals(expected, strategy.normalize(input), 0.001);
    }

    @Test
    void testMinMaxNormalizationIdenticalValues() {
        NormalizationStrategy strategy = new MinMaxNormalization();
        double[] input = {5.0, 5.0, 5.0};
        double[] expected = {0.0, 0.0, 0.0};
        
        assertArrayEquals(expected, strategy.normalize(input), 0.001);
    }

    @Test
    void testMeanCenteringNormalizationRegular() {
        NormalizationStrategy strategy = new MeanCenteringNormalization();
        double[] input = {1.0, 2.0, 6.0}; // Среднее: (1+2+6)/3 = 3
        double[] expected = {-2.0, -1.0, 3.0};
        
        assertArrayEquals(expected, strategy.normalize(input), 0.001);
    }

    @Test
    void testMeanCenteringNormalizationIdenticalValues() {
        NormalizationStrategy strategy = new MeanCenteringNormalization();
        double[] input = {4.0, 4.0, 4.0}; // Среднее: 4
        double[] expected = {0.0, 0.0, 0.0};
        
        assertArrayEquals(expected, strategy.normalize(input), 0.001);
    }

    @Test
    void testStrategyReplacementInProcessor() {
        double[] input = {1.0, 2.0, 3.0};

        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        double[] minMaxExpected = {0.0, 0.5, 1.0};
        assertArrayEquals(minMaxExpected, processor.process(input), 0.001);

        FeatureProcessor newProcessor = new FeatureProcessor(new MeanCenteringNormalization()); // Среднее = 2
        double[] meanExpected = {-1.0, 0.0, 1.0};
        assertArrayEquals(meanExpected, newProcessor.process(input), 0.001);
    }

    @Test
    void testInputArrayIsNotModified() {
        NormalizationStrategy strategy = new MinMaxNormalization();
        double[] input = {1.0, 2.0, 3.0};
        
        strategy.normalize(input);
        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, input, 0.001);
    }
}
