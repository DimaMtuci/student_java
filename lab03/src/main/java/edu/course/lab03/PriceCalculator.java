package edu.course.lab03;

public class PriceCalculator {
    private final DiscountPolicy discountPolicy;

    public PriceCalculator(DiscountPolicy discountPolicy) {
        if (discountPolicy == null) {
            throw new IllegalArgumentException("Политика скидок не может быть null");
        }
        this.discountPolicy = discountPolicy;
    }

    public double calculate(double price) {
        return discountPolicy.apply(price);
    }
}
