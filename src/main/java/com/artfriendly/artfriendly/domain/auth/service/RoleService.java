package com.artfriendly.artfriendly.domain.auth.service;

import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.service.MemberService;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import com.artfriendly.artfriendly.global.utils.CustomAuthorityUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleService {
    @Getter
    @Value("${admin.email}")
    String adminEmail;

    private final MemberService memberService;
    private final CustomAuthorityUtils customAuthorityUtils;

    @Transactional
    public void grantAdmin(long memberId) {
        Member member = memberService.findById(memberId);
        if(member.getEmail().equals(adminEmail)) {
            member.grantRoles(customAuthorityUtils.createAdminRoles());
        }
        else
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }
}
