package com.artfriendly.artfriendly.domain.userlog.repository;

import com.artfriendly.artfriendly.domain.userlog.entity.DailyUserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyUserCountRepository extends JpaRepository<DailyUserLog, Long> {
}
