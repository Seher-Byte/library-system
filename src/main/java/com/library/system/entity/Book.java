package com.library.system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Integer stockQuantity;

    public Book() {}

    // Constructor
    public Book(String title, String author, String isbn, Integer stockQuantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.stockQuantity = stockQuantity;
    }

    // Getters & Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) {
        if (stockQuantity < 0) throw new IllegalArgumentException("Stok negatif olamaz!");
        this.stockQuantity = stockQuantity;
    }
}
