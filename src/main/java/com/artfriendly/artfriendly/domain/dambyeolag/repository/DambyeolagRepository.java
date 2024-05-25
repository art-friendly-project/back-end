package com.artfriendly.artfriendly.domain.dambyeolag.repository;

import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DambyeolagRepository extends JpaRepository<Dambyeolag, Long> {
    @Query("SELECT d FROM Dambyeolag d " +
            "WHERE d.id = :dambyeolagId")
    Optional<Dambyeolag> findDambyeolagById(@Param("dambyeolagId") long dambyeolagId);

    // 담벼락 스티커와 북마크가 많이 된 것 부터 정렬
    @Query("SELECT d FROM Dambyeolag d LEFT JOIN d.stickerList s LEFT JOIN d.bookmarkList m " +
            "WHERE d.exhibition.id = :exhibitionId " +
            "GROUP BY d " +
            "ORDER BY (COUNT(s) + COUNT(m)) DESC")
    Page<Dambyeolag> findByOrderByStickerCountDesc(Pageable pageable, @Param("exhibitionId") long exhibitionId);

    // 최신순으로 정렬
    @Query("SELECT d FROM Dambyeolag d " +
            "WHERE d.exhibition.id = :exhibitionId " +
            "ORDER BY d.createTime DESC ")
    Page<Dambyeolag> findAllByOrderByCreateTimeDesc(Pageable pageable, @Param("exhibitionId") long exhibitionId);

    @Query("SELECT d FROM Dambyeolag d " +
            "WHERE d.member.id = :memberId " +
            "ORDER BY d.createTime DESC ")
    Page<Dambyeolag> findDambyeolagByMemberIdOrderByCreateTimeDesc(Pageable pageable, @Param("memberId") long memberId);
}
