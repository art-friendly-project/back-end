package com.artfriendly.artfriendly.domain.festival.repository;

import com.artfriendly.artfriendly.domain.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
}
