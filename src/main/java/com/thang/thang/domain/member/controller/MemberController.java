package com.thang.thang.domain.member.controller;

import com.thang.thang.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/member")
public class MemberController {

    @PostMapping("/join")
    public ResponseEntity joinMember(@RequestBody Member member){
        return ResponseEntity.ok().body(member);
    }

    @PostMapping("/login")
    public ResponseEntity loginMember(@RequestBody Member member){
        return ResponseEntity.ok().body(member);
    }

    @PostMapping("/logout")
    public ResponseEntity logoutMember(@RequestBody Member member){
        return ResponseEntity.ok().body(member);
    }
}
