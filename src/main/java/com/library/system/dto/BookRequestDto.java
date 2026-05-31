package com.library.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class BookRequestDto {

    @NotBlank(message = "Kitap adı boş olamaz!")
    private String title;

    @NotBlank(message = "Yazar adı boş olamaz!")
    private String author;

    @NotBlank(message = "ISBN alanı boş olamaz!")
    private String isbn;

    @NotNull(message = "Stok miktarı boş bırakılamaz!")
    @PositiveOrZero(message = "Stok miktarı negatif olamaz!")
    private Integer stockQuantity;

    public BookRequestDto() {}

    public BookRequestDto(String title, String author, String isbn, Integer stockQuantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.stockQuantity = stockQuantity;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
}
