package com.artfriendly.artfriendly.domain.mbti.repository;

import com.artfriendly.artfriendly.domain.mbti.entity.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    Mbti findByMbtiType(String mbtiType);
    @Query("SELECT size(mb.memberList) FROM Mbti mb")
    Double findAllCount();

    @Query("SELECT size(mb.memberList) FROM Mbti mb WHERE mb.id = :mbtiId")
    Double findCountByMbtiId(@Param("mbtiId") long mbtiId);

}
