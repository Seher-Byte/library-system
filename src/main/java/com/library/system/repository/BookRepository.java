package com.library.system.repository;

import com.library.system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // ISBN numarasına göre eşsiz kitap bulma
    Optional<Book> findByIsbn(String isbn);

    // Başlığa göre büyük/küçük harf duyarsız arama (Örn: "harry" yazınca Harry Potter'ı bulur)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Yazar ismine göre kitapları listeleme
    List<Book> findByAuthorIgnoreCase(String author);

    // Stokta olan kitapları getirme (stockQuantity > 0 olanlar)
    List<Book> findByStockQuantityGreaterThan(Integer amount);
}