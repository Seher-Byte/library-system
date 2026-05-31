package com.library.system.dto;

import jakarta.validation.constraints.NotNull;

public class LoanRequestDto {

    @NotNull(message = "Kitap ID alanı boş bırakılamaz!")
    private Long bookId;

    @NotNull(message = "Üye ID alanı boş bırakılamaz!")
    private Long memberId;


    public LoanRequestDto() {}

    public LoanRequestDto(Long bookId, Long memberId) {
        this.bookId = bookId;
        this.memberId = memberId;
    }

    // --- Getters & Setters ---
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
}