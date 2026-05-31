package com.library.system.controller;

import com.library.system.dto.MemberRequestDto;
import com.library.system.entity.Member;
import com.library.system.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@Valid @RequestBody MemberRequestDto dto) {
        Member member = new Member(dto.getFirstName(), dto.getLastName(), dto.getEmail());
        Member savedMember = memberService.saveMember(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}