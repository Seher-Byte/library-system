package com.library.system.service;

import com.library.system.entity.Book;
import com.library.system.entity.Loan;
import com.library.system.entity.Member;
import com.library.system.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final MemberService memberService;


    public LoanService(LoanRepository loanRepository, BookService bookService, MemberService memberService) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @Transactional
    public Loan borrowBook(Long bookId, Long memberId) {
        Book book = bookService.findById(bookId);
        Member member = memberService.findById(memberId);


        if (book.getStockQuantity() <= 0) {
            throw new RuntimeException("Bu kitap şu anda stokta bulunmamaktadır!");
        }

        if (loanRepository.existsByBookIdAndReturnDateIsNull(bookId)) {

            if (book.getStockQuantity() == 1) {
                throw new RuntimeException("Bu kitabın son kopyası şu an başka bir üyede!");
            }
        }


        book.setStockQuantity(book.getStockQuantity() - 1);
        bookService.saveBook(book); // Güncel stoğu kaydet


        Loan loan = new Loan(book, member, LocalDate.now());
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Ödünç kaydı bulunamadı! ID: " + loanId));

        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Bu kitap zaten iade edilmiş!");
        }


        loan.setReturnDate(LocalDate.now());


        Book book = loan.getBook();
        book.setStockQuantity(book.getStockQuantity() + 1);
        bookService.saveBook(book);

        return loanRepository.save(loan);
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findByReturnDateIsNull();
    }

    public List<Loan> getMemberLoans(Long memberId) {
        return loanRepository.findByMemberId(memberId);
    }
}