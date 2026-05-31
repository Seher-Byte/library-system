package com.library.system.repository;

import com.library.system.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByMemberId(Long memberId);

    List<Loan> findByBookId(Long bookId);

    List<Loan> findByReturnDateIsNull();

    List<Loan> findByMemberIdAndReturnDateIsNull(Long memberId);

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}