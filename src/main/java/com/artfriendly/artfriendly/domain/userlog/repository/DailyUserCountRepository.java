package com.artfriendly.artfriendly.domain.userlog.repository;

import com.artfriendly.artfriendly.domain.userlog.entity.DailyUserCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyUserCountRepository extends JpaRepository<DailyUserCount, Long> {
}
