package com.artfriendly.artfriendly.domain.term.service;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.domain.term.dto.MemberTermReqDto;
import com.artfriendly.artfriendly.domain.term.entity.MemberTerm;
import com.artfriendly.artfriendly.domain.term.entity.Term;
import com.artfriendly.artfriendly.domain.term.repository.MemberTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberTermService {
    private final MemberTermRepository memberTermRepository;
    private final MemberService memberService;
    private final TermService termService;

    @Transactional
    public void createMemberTerm(long memberId, MemberTermReqDto memberTermReqDto) {
        List<MemberTerm> memberTermList = new ArrayList<>();
        Member member = memberService.findById(memberId);

        for(Integer termId : memberTermReqDto.termIdList()) {
            Term term = termService.getTerm(termId);

            MemberTerm memberTerm = MemberTerm.builder()
                    .member(member)
                    .term(term)
                    .build();

            memberTermList.add(memberTerm);
        }

        memberTermRepository.saveAll(memberTermList);
    }
}
