package com.artfriendly.artfriendly.global.init;

import com.artfriendly.artfriendly.domain.exhibition.service.ExhibitionService;
import com.artfriendly.artfriendly.domain.mbti.repository.MbtiRepository;
import com.artfriendly.artfriendly.domain.mbti.service.MbtiService;
import com.artfriendly.artfriendly.domain.member.repository.WithdrawalReasonRepository;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.domain.term.repository.TermRepository;
import com.artfriendly.artfriendly.domain.term.service.TermService;
import com.artfriendly.artfriendly.domain.userlog.service.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final MbtiRepository mbtiRepository;
    private final WithdrawalReasonRepository withdrawalReasonRepository;
    private final TermRepository termRepository;
    private final MemberService memberService;
    private final MbtiService mbtiService;
    private final ExhibitionService exhibitionService;
    private final TermService termService;
    private final UserLogService userLogService;

    @Override
    public void run(String... args) throws Exception {
        if(mbtiRepository.count() == 0) {
            mbtiService.initMbit();
        }
        if(withdrawalReasonRepository.count() == 0) {
            memberService.initWithdrawalReason();
        }
        if(termRepository.count() == 0) {
            termService.initTerms();
        }
        exhibitionService.clearPopularExhibitionCache();
        userLogService.resetDailyUserCountCache();
    }
}
