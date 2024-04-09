package com.artfriendly.artfriendly.domain.mbti.repository;

import com.artfriendly.artfriendly.domain.mbti.entity.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    Mbti findByMbtiType(String mbtiType);
}
