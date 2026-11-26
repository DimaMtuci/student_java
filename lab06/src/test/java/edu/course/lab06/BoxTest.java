package edu.course.lab06;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoxTest {

    @Test
    void testStringBox() {
        Box<String> stringBox = new Box<>("Hello Generics");
        assertEquals("Hello Generics", stringBox.get());

        stringBox.set("Updated");
        assertEquals("Updated", stringBox.get());

        
        
    }

    @Test
    void testIntegerBox() {
        Box<Integer> intBox = new Box<>(42);
        assertEquals(42, intBox.get());

        intBox.set(100);
        assertEquals(100, intBox.get());
    }
}
