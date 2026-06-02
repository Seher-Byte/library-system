package com.library.system.service;

import com.library.system.exception.ResourceNotFoundException;
import com.library.system.entity.Book;
import com.library.system.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book saveBook(Book book) {
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("Bu ISBN numarasına sahip bir kitap zaten mevcut!");
        }
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kitap bulunamadı! ID: " + id));
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }


    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Silinmek istenen kitap bulunamadı! ID: " + id);
        }
        bookRepository.deleteById(id);
    }


    @Transactional
    public com.library.system.entity.Book updateBook(Long id, com.library.system.dto.BookRequestDto dto) {
        com.library.system.entity.Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new com.library.system.exception.ResourceNotFoundException("Güncellenmek istenen kitap bulunamadı! ID: " + id));

        existingBook.setTitle(dto.getTitle());
        existingBook.setAuthor(dto.getAuthor());
        existingBook.setIsbn(dto.getIsbn());
        existingBook.setStockQuantity(dto.getStockQuantity());

        return bookRepository.save(existingBook);
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Bu ISBN numarasına sahip bir kitap bulunamadı: " + isbn));
    }
}