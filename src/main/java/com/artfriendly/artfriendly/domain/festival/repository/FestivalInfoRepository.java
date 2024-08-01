package com.artfriendly.artfriendly.domain.festival.repository;

import com.artfriendly.artfriendly.domain.festival.entity.FestivalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalInfoRepository extends JpaRepository<FestivalInfo, Long> {
}
