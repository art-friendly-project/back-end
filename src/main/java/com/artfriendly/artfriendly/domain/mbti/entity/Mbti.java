package com.artfriendly.artfriendly.domain.mbti.entity;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mbti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String mbtiType;

    @Column(nullable = false)
    private String subTitle;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_type")
    private Mbti matchType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "miss_match_type")
    private Mbti missMatchType;

    // 연관관계
    @OneToMany(mappedBy = "mbti", fetch = FetchType.LAZY)
    List<Member> memberList;

    @Builder
    public Mbti(Long id, String mbtiType, String subTitle, String title, String body, String imageUrl, Mbti matchType, Mbti missMatchType) {
        this.id = id;
        this.mbtiType = mbtiType;
        this.subTitle = subTitle;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.matchType = matchType;
        this.missMatchType = missMatchType;
    }

    public void setMatchType(Mbti matchType) {
        this.matchType = matchType;
    }
    public void setMissMatchType(Mbti missMatchType) {
        this.missMatchType = missMatchType;
    }
}
