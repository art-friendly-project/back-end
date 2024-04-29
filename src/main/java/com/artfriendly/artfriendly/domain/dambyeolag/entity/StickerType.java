package com.artfriendly.artfriendly.domain.dambyeolag.entity;

import lombok.Getter;

@Getter
public enum StickerType {
    ANGRY("angry"),
    ARROW("arrow"),
    CHECK("check"),
    CIRCLE("circle"),
    CLOUD("cloud"),
    CLOVER("clover"),
    FLOWER("flower"),
    FOURDOT("fourDot"),
    HEART("heart"),
    NORMAL("normal"),
    SHINE("shine"),
    SMILE("smile"),
    SPRING("spring"),
    STAR("star"),
    SUN("sun"),
    VERTICAL("vertical");


    private final String stickerName;

    StickerType(String stickerName) { this.stickerName = stickerName; }
}
