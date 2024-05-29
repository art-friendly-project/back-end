package com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DambyeolagUpdateDto(
        @NotBlank(message = "제목을 입력해주세요.")
        String title,
        @NotBlank(message = "내용을 입력해주세요.")
        String body,
        @NotNull(message = "담벼락 id값을 입력해주세요.")
        long dambyeolagId
) {
}
