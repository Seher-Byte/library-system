package com.library.system.service;

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
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı! ID: " + id));
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
            throw new RuntimeException("Silinmek istenen kitap bulunamadı!");
        }
        bookRepository.deleteById(id);
    }
}