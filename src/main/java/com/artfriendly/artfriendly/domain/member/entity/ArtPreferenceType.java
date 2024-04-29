package com.artfriendly.artfriendly.domain.member.entity;

import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public enum ArtPreferenceType {
    PAINTING("회화"),
    PHOTOGRAPHY("사진"),
    GRAPHIC("그래픽"),
    DECORATIVE_ART("장식미술"),
    SCULPTURE("조각"),
    CRAFT("공예"),
    ARCHITECTURE("건축"),
    DESIGN("디자인"),
    PRINTMAKING("판화"),
    MUSIC("음악"),
    FESTIVAL("축제"),
    THEATER("연극"),
    MUSICAL("뮤지컬"),
    FILM("영화"),
    BROADCAST("방송"),
    LITERATURE("문학"),
    COMICS("만화/웹툰"),
    DANCE("무용");

    private final String description;

    ArtPreferenceType(String description) {
        this.description = description;
    }

    public static void check(String text) {
        for (ArtPreferenceType artPreferenceType : ArtPreferenceType.values()) {
            if (artPreferenceType.description.equals(text)) {
                return;
            }
        }
        throw new BusinessException(ErrorCode.ARTPREFERENCE_NOT_FOUND);
    }
}
