package com.artfriendly.artfriendly.domain.dambyeolag.entity;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int xCoordinate;

    @Column(nullable = false)
    private int yCoordinate;

    @Column
    private String body;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "dambyeolag_id")
    private Dambyeolag dambyeolag;

    // 이미지 연관관계

    @Builder
    public Sticker(Long id, int xCoordinate, int yCoordinate, String body, Member member, Dambyeolag dambyeolag) {
        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.body = body;
        this.member = member;
        this.dambyeolag = dambyeolag;
    }
}
