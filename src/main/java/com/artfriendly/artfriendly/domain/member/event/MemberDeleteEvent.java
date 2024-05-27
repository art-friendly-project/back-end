package com.artfriendly.artfriendly.domain.member.event;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberDeleteEvent extends ApplicationEvent {
    private final String message;
    private final transient Member member;

    public MemberDeleteEvent(Object source, String message, Member member) {
        super(source);
        this.message = message;
        this.member = member;
    }

}
