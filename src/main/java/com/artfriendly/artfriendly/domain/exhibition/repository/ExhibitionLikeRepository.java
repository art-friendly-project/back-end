package com.artfriendly.artfriendly.domain.exhibition.repository;

import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExhibitionLikeRepository extends JpaRepository<ExhibitionLike, Long> {
    @Query("SELECT el FROM ExhibitionLike el " +
            "JOIN FETCH el.member m " +
            "JOIN FETCH el.exhibition e " +
            "WHERE e.id = :exhibitionId " +
            "AND m.id = :memberId")
    Optional<ExhibitionLike> findExhibitionLikeByMemberIdAndExhibitionId(@Param("memberId") long memberId, @Param("exhibitionId") long exhibitionId);
}
