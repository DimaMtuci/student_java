package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBooksWithSameIsbnAreEqualAndHaveSameHashCode() {
        Book book1 = new Book("978-5-1234", "Java Core");
        Book book2 = new Book("978-5-1234", "Java Advanced Collection");
        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testHashMapLookupByDifferentObjectWithSameIsbn() {
        Map<Book, String> library = new HashMap<>();
        Book bookInMap = new Book("978-5-0000", "Original Title");
        library.put(bookInMap, "Shelf A");
        Book searchKey = new Book("978-5-0000", "Different Title");
        assertEquals("Shelf A", library.get(searchKey));
    }
    @Test
    void testBooksWithDifferentIsbnAreNotEqual() {
        Book book1 = new Book("978-5-1111", "Some Book");
        Book book2 = new Book("978-5-2222", "Some Book");
        assertNotEquals(book1, book2);
    }
}
