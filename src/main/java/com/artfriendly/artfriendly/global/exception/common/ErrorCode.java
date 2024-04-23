package com.artfriendly.artfriendly.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Jackson HTTP BODY 파싱
    HTTP_MESSAGE_NOT_READABLE(400, "요청값을 읽어들이지 못했습니다. 형식을 확인해 주세요."),

    // 회원가입
    EMAIL_DUPLICATION(400, "이미 존재하는 이메일입니다."),
    OAUTH2_PLATFORM_MISMATCH(400, "로그인 연동 플랫폼이 잘못되었습니다."),

    // 인증 - 로그인 시도
    MISMATCHED_SIGNIN_INFO(401, "잘못된 로그인 정보입니다."),
    OAUTH2_LOGIN_FAIL(401, "OAuth2 로그인 인증에 실패했습니다."),
    USER_NOT_FOUND_AT_LOGIN(401, "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요."),
    MISMATCHED_SIGNIN_TYPE(401, "이미 가입된 회원입니다. 다른 로그인 방법을 확인해주세요."),

    // 인증 - 토큰
    NOT_EXISTS_AUTH_HEADER(401, "Authorization Header가 빈 값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(401, "인증 타입이 Bearer 타입이 아닙니다."),
    TOKEN_EXPIRED(401, "해당 token은 만료되었습니다."),
    INVALID_SIGNATURE(401, "토큰의 서명이 유효하지 않습니다."),
    MALFORMED_TOKEN(401, "잘못된 형식의 JWT가 주어졌습니다."),
    ACCESS_TOKEN_EXPIRED(401, "해당 access token은 만료되었습니다."),
    NOT_ACCESS_TOKEN_TYPE(401, "tokenType이 access token이 아닙니다."),
    REFRESH_TOKEN_EXPIRED(401, "해당 refresh token은 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(400, "해당 refresh token은 존재하지 않습니다."),
    NOT_VALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    ACCESS_DENIED(403, "인증은 되어 있지만, 특정 리소스에 대한 접근 권한이 없습니다."),

    // 사용자
    USER_NOT_FOUND(404, "해당 사용자가 존재하지 않습니다."),

    // 파일
    INVALID_FILE_EXTENSION(400, "파일 확장자가 유효하지 않습니다."),
    FILE_NOT_FOUND(400, "해당 파일이 존재하지 않습니다."),
    FILE_CANNOT_BE_STORED(500, "파일을 저장할 수 없습니다."),
    FILE_CANNOT_BE_READ(500, "파일을 읽을 수 없습니다."),
    FILE_CANNOT_BE_SENT(500, "읽어들인 파일을 전송할 수 없습니다"),
    MULTIPART_FILE_CANNOT_BE_READ(500, "파일을 읽을 수 없습니다."),
    FILE_CANNOT_BE_DELETED(500, "파일을 삭제할 수 없습니다."),

    // INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생하였습니다."),

    // utils/converter
    DATETIME_IS_NULL(401, "DateTime이 NULL값입니다."),
    DATE_IS_NULL(401, "Date가 NULL값입니다."),

    // utils/LocalDateFormatter
    WRONG_DATE_FORMAT(401, "잘못된 DateTimeFormatter 입니다."),

    //인코딩 에러
    UNSUPPORTED_ENCODING_DATA(401, "인코딩 할 수 없는 데이터입니다."),

    // Dambyeolag
    DAMBYEOLAG_NOT_FOUND(404, "담벼락이 존재하지 않습니다."),
    DAMBYEOLAG_NOT_ACCESS(403, "해당 담벼락 관련 권한이 없습니다."),
    DAMBYEOLAGBOOKMARK_NOT_FOUND(404, "해당 북마크가 존재하지 않습니다."),
    DAMBYEOLAGBOOKMARK_NOT_ACCESS(403, "해당 북마크 관련 권한이 없습니다."),

    // Sticker
    STICKER_NOT_FOUND(404, "스티커가 존재하지 않습니다."),
    STICKER_NOT_ACCESS(403, "해당 스티커 관련 권한이 없습니다."),

    // ApiIntegration 에러
    API_CALL_FAILED(401, "API 호출에 실패했습니다."),
    API_INTEGRATE_FAILED(401, "API 연동에 실패했습니다.(멀티 스레드)"),

    // Exhibition 에러
    EXHIBITION_NOT_FOUND(404, "해당 전시 정보를 찾을 수 없습니다."),
    EXIST_EXHIBITIONLIKE(400, "이미 좋아요가 추가되어 있습니다."),
    NOT_EXIST_EXHIBITIONLIKE(400, "해당 좋아요 정보가 없습니다."),
    HOPEINDEX_NOT_FOUND(404, "해당 희망 Index가 없습니다"),
    EXIST_EXHIBITIONHOPE(400, "이미 전시 희망 사항이 추가되어 있습니다."),
    NOT_EXIST_EXHIBITIONHOPE(400, "해당 희망 사항 정보가 없습니다"),
    SAME_EXHIBITIONHOPE(400, "이미 동일한 희망 사항이 적용되어있습니다."),

    // Mbti 에러
    MBTI_NOT_FOUND(404, "해당 MBTI 정보를 찾을 수 없습니다."),

    // 전시 취향 에러
    ARTPREFERENCE_NOT_FOUND(404, "해당 전시 취향 정보를 찾을 수 없습니다."),

    // Team
    TEAM_NOT_FOUND(404, "팀이 존재하지 않습니다."),
    TEAM_UPDATE_DENIED(403, "팀 수정 권한이 없습니다."),
    LEADER_CANNOT_LEAVE_TEAM(400, "팀장은 팀을 나갈 수 없습니다."),
    INVALID_INVITE_CODE(404, "유효하지 않은 초대코드입니다."),
    ALREADY_IN_TEAM(400, "이미 가입된 팀입니다."),
    TEAM_CODE_EXPIRED(401, "초대코드 유효기간이 지났습니다. 다시 생성해주세요."),
    NOT_EXISTS_IN_TEAM(404, "해당 팀에 속하지 않습니다.");


    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
