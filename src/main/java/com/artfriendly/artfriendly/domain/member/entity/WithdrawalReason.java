package com.artfriendly.artfriendly.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawalReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String reason;

    @Column
    private int count;

    @Builder
    public WithdrawalReason(Long id, String reason, int count) {
        this.id = id;
        this.reason = reason;
        this.count = count;
    }

    public void increaseCount() { this.count++; }
}
