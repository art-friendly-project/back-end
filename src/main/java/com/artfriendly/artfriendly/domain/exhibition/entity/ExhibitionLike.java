package com.artfriendly.artfriendly.domain.exhibition.entity;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExhibitionLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    public ExhibitionLike(Long id, Member member, Exhibition exhibition) {
        this.id = id;
        this.member = member;
        this.exhibition = exhibition;
    }
}
