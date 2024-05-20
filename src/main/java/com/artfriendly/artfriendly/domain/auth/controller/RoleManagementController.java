package com.artfriendly.artfriendly.domain.auth.controller;

import com.artfriendly.artfriendly.domain.auth.service.RoleService;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("role")
public class RoleManagementController {
    private final RoleService roleService;

    @PostMapping("/admin")
    public RspTemplate<Void> grantAdmin(@AuthenticationPrincipal long memberId) {
        roleService.grantAdmin(memberId);
        return new RspTemplate(HttpStatus.OK,"어드민 권한 부여 완료");
    }
}
