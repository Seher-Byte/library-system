package com.library.system.repository;

import com.library.system.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // E-posta ile tam eşleşen üyeyi bulma
    Optional<Member> findByEmail(String email);

    // Bir e-postanın sistemde kayıtlı olup olmadığını hızlıca kontrol etme (boolean döner)
    boolean existsByEmail(String email);
}