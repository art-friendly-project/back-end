package com.artfriendly.artfriendly.domain.exhibition.repository;

import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionHope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExhibitionHopeRepository extends JpaRepository<ExhibitionHope, Long> {
    @Query("SELECT eh FROM ExhibitionHope eh " +
            "JOIN FETCH eh.member m " +
            "JOIN FETCH eh.exhibition e " +
            "WHERE e.id = :exhibitionId " +
            "AND m.id = :memberId")
    Optional<ExhibitionHope> findExhibitionHopeByMemberIdAndExhibitionId(@Param("memberId") long memberId, @Param("exhibitionId") long exhibitionId);
}
