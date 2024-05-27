package com.artfriendly.artfriendly.domain.member.event;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void memberDeleteEventPublish(String message, Member member) {
        MemberDeleteEvent memberDeleteEvent = new MemberDeleteEvent(this, message, member);
        applicationEventPublisher.publishEvent(memberDeleteEvent);
    }
}
