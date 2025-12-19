package com.thang.thang.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "ban_status", nullable = false)
    private BanStatus banStatus = BanStatus.ACTIVE;

    @Column(name = "ban_expires_at")
    private LocalDateTime banExpiresAt;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public Member(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
