package edu.course.lab04;

import java.util.Objects;

public final class Book {
    private final String isbn;
    private final String title;
    public Book(String isbn, String title) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN не может быть null");
        }
        this.isbn = isbn;
        this.title = title;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
