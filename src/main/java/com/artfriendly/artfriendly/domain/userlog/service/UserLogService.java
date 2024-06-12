package com.artfriendly.artfriendly.domain.userlog.service;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.domain.userlog.cache.DailyUserCountCache;
import com.artfriendly.artfriendly.domain.userlog.entity.DailyUserLog;
import com.artfriendly.artfriendly.domain.userlog.entity.LocationInfoLog;
import com.artfriendly.artfriendly.domain.userlog.repository.DailyUserCountRepository;
import com.artfriendly.artfriendly.domain.userlog.repository.LocationInfoLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserLogService {
    private final DailyUserCountCache dailyUserCountCache;
    private final DailyUserCountRepository dailyUserCountRepository;
    private final MemberService memberService;
    private final LocationInfoLogRepository locationInfoLogRepository;

    public void upUserCount() {
        dailyUserCountCache.upDailyUserCount();

    }

    public void resetDailyUserCountCache() {
        dailyUserCountCache.clearDailyUserCountCache();
        dailyUserCountCache.initCache();
    }

    @Transactional
    public void saveDailyUserCount() {
        DailyUserLog dailyUserLog = DailyUserLog.builder()
                .userCount(dailyUserCountCache.getDailyUserCount())
                .build();

        dailyUserCountRepository.save(dailyUserLog);
    }

    @Transactional
    public void createLocationInfoLog(long memberId) {
        Member member = memberService.findById(memberId);

        LocationInfoLog locationInfoLog = LocationInfoLog.builder()
                .member(member)
                .build();

        locationInfoLogRepository.save(locationInfoLog);
    }
}
