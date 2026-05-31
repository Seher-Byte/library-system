package com.library.system.service;

import com.library.system.entity.Member;
import com.library.system.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member saveMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException("Bu e-posta adresi ile kayıtlı bir üye zaten var!");
        }
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Üye bulunamadı! ID: " + id));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Silinmek istenen üye bulunamadı!");
        }
        memberRepository.deleteById(id);
    }
}