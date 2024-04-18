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
public class ExhibitionHope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Enumerated(value = EnumType.STRING)
    private Hope hope;


    @Builder
    public ExhibitionHope(Long id, Member member, Exhibition exhibition, Hope hope) {
        this.id = id;
        this.member = member;
        this.exhibition = exhibition;
        this.hope = hope;
    }

    public void updateHope(Hope hope) {
        this.hope = hope;
    }

    @Getter
    public enum Hope {
        WANT_TO_SEE(1.5, "보고 싶어요"),
        GOOD(1, "좋아해요"),
        INTERESTING(0.5, "관심 있어요"),
        SO_SO(-0.5, "그냥 그래요"),
        NOT_GOOD(-1, "안 갈래요");

        private final double hopeRating;
        private final String massage;

        Hope(double hopeRating, String massage) {
            this.hopeRating = hopeRating;
            this.massage = massage;
        }
    }
}
