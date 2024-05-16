package com.artfriendly.artfriendly.domain.member.Event;

import com.artfriendly.artfriendly.domain.dambyeolag.service.dambyeolag.DambyeolagService;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class MemberEventListener {

    private final MemberService memberService;
    private final DambyeolagService dambyeolagService;

    @EventListener
    public void deleteMemberData(MemberDeleteEvent memberDeleteEvent) {
        memberService.deleteMemberImage(memberDeleteEvent.getMember());
        dambyeolagService.deleteBookmarksByMember(memberDeleteEvent.getMember());
    }
}
