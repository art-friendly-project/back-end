package com.artfriendly.artfriendly.domain.term.repository;

import com.artfriendly.artfriendly.domain.term.entity.MemberTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberTermRepository extends JpaRepository<MemberTerm, Long> {
    List<MemberTerm> findMemberTermByMemberId(Long memberId);
}
