package edu.course.lab03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscountPolicyTest {

    @Test
    void testNoDiscountReturnsSamePrice() {
        DiscountPolicy policy = new NoDiscount();
        PriceCalculator calculator = new PriceCalculator(policy);
        
        assertEquals(100.0, calculator.calculate(100.0), 0.001);
    }

    @Test
    void testPercentDiscountAppliesCorrectly() {
        DiscountPolicy policy = new PercentDiscount(20.0); // 20%
        PriceCalculator calculator = new PriceCalculator(policy);
        
        assertEquals(80.0, calculator.calculate(100.0), 0.001);
    }

    @Test
    void testPolicyReplacement() {
        
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        assertEquals(100.0, calculator.calculate(100.0), 0.001);

        
        PriceCalculator newCalculator = new PriceCalculator(new PercentDiscount(50.0));
        assertEquals(50.0, newCalculator.calculate(100.0), 0.001);
    }

    @Test
    void testNegativePriceThrowsException() {
        DiscountPolicy policy = new NoDiscount();
        assertThrows(IllegalArgumentException.class, () -> policy.apply(-10.0));
    }
}
