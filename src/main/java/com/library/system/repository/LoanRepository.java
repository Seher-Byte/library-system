package com.library.system.repository;

import com.library.system.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Bir üyenin aldığı tüm ödünç işlemlerini listeleme
    List<Loan> findByMemberId(Long memberId);

    // Bir kitabın daha önce kimler tarafından alındığını listeleme
    List<Loan> findByBookId(Long bookId);

    // Henüz iade edilmemiş (returnDate == null) tüm kitapları bulma
    List<Loan> findByReturnDateIsNull();

    // Belirli bir üyenin henüz iade etmediği kitapları bulma
    List<Loan> findByMemberIdAndReturnDateIsNull(Long memberId);

    // Belirli bir kitabın şu an birisinde olup olmadığını (iade edilmemiş) kontrol etme
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}