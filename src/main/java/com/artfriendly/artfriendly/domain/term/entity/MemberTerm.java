package com.artfriendly.artfriendly.domain.term.entity;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne
    @JoinColumn(name = "term_id")
    Term term;

    @Builder
    public MemberTerm(Long id, Member member, Term term) {
        this.id = id;
        this.member = member;
        this.term = term;
    }
}
