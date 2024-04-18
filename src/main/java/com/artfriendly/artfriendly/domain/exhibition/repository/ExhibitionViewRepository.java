package com.artfriendly.artfriendly.domain.exhibition.repository;

import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExhibitionViewRepository extends JpaRepository<ExhibitionView, Long> {
    @Query("SELECT ev FROM ExhibitionView ev " +
            "JOIN FETCH ev.member m " +
            "JOIN FETCH ev.exhibition e " +
            "WHERE e.id = :exhibitionId " +
            "AND m.id = :memberId")
    Optional<ExhibitionView> findExhibitionViewByMemberIdAndExhibitionId(@Param("memberId") long memberId, @Param("exhibitionId") long exhibitionId);
}
