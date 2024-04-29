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
    private String body;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private StickerType stickerType;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "dambyeolag_id")
    private Dambyeolag dambyeolag;

    @Builder
    public Sticker(Long id, String body, StickerType stickerType, Member member, Dambyeolag dambyeolag) {
        this.id = id;
        this.body = body;
        this.stickerType = stickerType;
        this.member = member;
        this.dambyeolag = dambyeolag;
    }
}
