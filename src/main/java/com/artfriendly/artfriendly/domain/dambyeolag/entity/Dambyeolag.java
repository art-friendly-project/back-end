package com.artfriendly.artfriendly.domain.dambyeolag.entity;

import com.artfriendly.artfriendly.domain.common.BaseTimeEntity;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dambyeolag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    // 연관관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    @OneToMany(mappedBy = "dambyeolag", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    List<DambyeolagBookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "dambyeolag", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    List<Sticker> stickerList = new ArrayList<>();

    // 전시 정보 연동 추가해야함
    @Column(nullable = false)
    private Long exhibitionId;

    @Builder
    public Dambyeolag(Long id, String title, String body, Member member, Long exhibitionId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.member = member;
        this.exhibitionId = exhibitionId;
    }

    public void addBookmark(DambyeolagBookmark bookmark) { this.bookmarkList.add(bookmark); }
    public void addSticker(Sticker sticker) { this.stickerList.add(sticker); }
}
