package com.library.system.controller;

import com.library.system.dto.BookRequestDto;
import com.library.system.entity.Book;
import com.library.system.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value; // 🤖 AI ENTEGRASYONU: Groq API key okumak için import edildi
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    // 🤖 AI ENTEGRASYONU: application.properties içindeki Groq API anahtarını bağlıyoruz
    @Value("${groq.api.key}")
    private String apiKey;

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 🤖 AI ENTEGRASYONU: Kullanıcının doğal dil aramasını alıp Groq (Llama 3) ile eşleştiren endpoint
    @PostMapping("/ai-search")
    public ResponseEntity<?> aiSearchBooks(@RequestBody Map<String, String> request) {
        String userQuery = request.get("query");
        if (userQuery == null || userQuery.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Arama sorgusu boş olamaz."));
        }

        // 1. Veritabanındaki tüm mevcut kitapları çekiyoruz
        List<Book> allBooks = bookService.findAll();
        StringBuilder bookListText = new StringBuilder();
        for (Book b : allBooks) {
            bookListText.append("ID: ").append(b.getId())
                    .append(" | Başlık: ").append(b.getTitle())
                    .append(" | Yazar: ").append(b.getAuthor()).append("\n");
        }

        // 2. Llama-3 modeline tam uyması için promptu hazırlıyoruz
        String systemPrompt = "Sen deneyimli bir kütüphane asistanısın. Kitaplar hakkında derin bilgin var.\n\n" +
                "Kullanıcı şunu arıyor: '" + userQuery + "'\n\n" +
                "Kütüphanedeki mevcut kitaplar:\n" +
                bookListText.toString() + "\n" +
                "GÖREV: Kullanıcının arama sorgusuna anlam, tema, duygu ve konu bakımından uyan kitapların ID'lerini bul.\n" +
                "ÖNEMLİ KURALLAR:\n" +
                "- Sadece başlık eşleşmesi arama, konuyu ve temayı düşün\n" +
                "- 'aşk kitabı' araması için aşk, romans, duygusal ilişki temalı kitapları öner\n" +
                "- 'macera' araması için serüven, yolculuk, aksiyon temalı kitapları öner\n" +
                "- Türkçe ve yabancı dil kitap isimlerini de anlayarak eşleştir\n" +
                "- Emin olmasan bile benzer temadaki kitapları öner\n\n" +
                "CEVAP FORMATI: Sadece ID numaralarını virgülle yaz (örn: 1,3,5). Hiçbir kitap uymuyorsa sadece 'YOK' yaz.\n" +
                "Açıklama, cümle, markdown KESİNLİKLE yazma.";

        String aiResponse = "";
        try {
            // Groq API'nin standart ve kararlı endpoint URL'i
            String url = "https://api.groq.com/openai/v1/chat/completions";

            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();

            // Groq (OpenAI standartlarında) JSON istek gövdesini hazırlıyoruz
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama-3.3-70b-versatile"); // Çok hızlı ve kararlı çalışan Llama 3 modeli

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "user", "content", systemPrompt));
            requestBody.put("messages", messages);

            // Groq API bizden Authorization header'ı (Bearer Token) istiyor, onu ekliyoruz
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            org.springframework.http.HttpEntity<Map<String, Object>> entity = new org.springframework.http.HttpEntity<>(requestBody, headers);

            org.springframework.http.ResponseEntity<String> response =
                    restTemplate.postForEntity(url, entity, String.class);

            if (response.getBody() != null) {
                String body = response.getBody();
                int contentStart = body.indexOf("\"content\":\"") + 11;
                int contentEnd   = body.indexOf("\"", contentStart);
                aiResponse = body.substring(contentStart, contentEnd).trim();
            }

        } catch (Exception e) {
            System.out.println("🚨 [GROQ AI SEARCH HATASI]: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Yapay zeka servisinde teknik bir hata oluştu."));
        }

        // 3. Yapay zekanın kararına göre veritabanından filtreleme yapıyoruz
        List<Book> matchedBooks = new ArrayList<>();
        if (!aiResponse.equalsIgnoreCase("YOK") && !aiResponse.isEmpty()) {
            String[] ids = aiResponse.split(",");
            for (String idStr : ids) {
                try {
                    Long id = Long.parseLong(idStr.trim());
                    Book foundBook = bookService.findById(id);
                    if (foundBook != null) {
                        matchedBooks.add(foundBook);
                    }
                } catch (Exception ignored) {}
            }
        }

        return ResponseEntity.ok(matchedBooks);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequestDto dto) {
        Book book = new Book(dto.getTitle(), dto.getAuthor(), dto.getIsbn(), dto.getStockQuantity());
        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody com.library.system.dto.BookRequestDto dto) {

        var updatedBook = bookService.updateBook(id, dto);
        return ResponseEntity.ok(updatedBook); // HTTP 200 OK döner
    }

    @GetMapping("/isbn")
    public ResponseEntity<Book> getBookByIsbn(@RequestParam String isbn) {
        Book book = bookService.findByIsbn(isbn);
        return ResponseEntity.ok(book);
    }
}