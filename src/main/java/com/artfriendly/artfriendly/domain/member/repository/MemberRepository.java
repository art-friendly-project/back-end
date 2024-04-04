package com.artfriendly.artfriendly.domain.member.repository;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m " +
            "WHERE m.email = :email ")
    Optional<Member> findOptionalMemberByEmail(@Param("email") String email);

    @Query("SELECT m FROM Member m " +
            "WHERE m.id = :memberId ")
    Optional<Member> findOptionalMemberById(@Param("memberId") Long memberId);

    @Query("SELECT m FROM Member m " +
            "WHERE m.id = :memberId ")
    Member findMemberById(@Param("memberId") Long memberId);
}
