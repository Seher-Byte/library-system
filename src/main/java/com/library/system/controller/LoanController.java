package com.library.system.controller;

import com.library.system.dto.LoanRequestDto;
import com.library.system.entity.Loan;
import com.library.system.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/borrow")

    public ResponseEntity<Loan> borrowBook(@Valid @RequestBody LoanRequestDto dto) {
        Loan loan = loanService.borrowBook(dto.getBookId(), dto.getMemberId());
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    @PostMapping("/return/{loanId}")
    public ResponseEntity<Loan> returnBook(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.returnBook(loanId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Loan>> getMemberLoans(@PathVariable Long memberId) {
        return ResponseEntity.ok(loanService.getMemberLoans(memberId));
    }
}