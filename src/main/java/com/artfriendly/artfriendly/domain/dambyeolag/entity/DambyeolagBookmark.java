package com.artfriendly.artfriendly.domain.dambyeolag.entity;

import com.artfriendly.artfriendly.domain.common.BaseTimeEntity;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DambyeolagBookmark extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne
    @JoinColumn(name = "dambyeolag_id")
    Dambyeolag dambyeolag;

    @Builder
    public DambyeolagBookmark(Long id, Member member, Dambyeolag dambyeolag) {
        this.id = id;
        this.member = member;
        this.dambyeolag = dambyeolag;
    }
}
