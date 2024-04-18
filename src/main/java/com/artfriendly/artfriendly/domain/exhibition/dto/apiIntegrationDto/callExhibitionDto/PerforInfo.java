package com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@XmlAccessorType(XmlAccessType.FIELD)
public class PerforInfo {
    @XmlElement(name = "seq")
    private int seq;

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "startDate")
    private String startDate;

    @XmlElement(name = "endDate")
    private String endDate;

    @XmlElement(name = "place")
    private String place;

    @XmlElement(name = "realmName")
    private String realmName;

    @XmlElement(name = "area")
    private String area;

    @XmlElement(name = "subTitle")
    private String subTitle;

    @XmlElement(name = "price")
    private String price;

    @XmlElement(name = "contents1")
    private String contents1;

    @XmlElement(name = "contents2")
    private String contents2;

    @XmlElement(name = "url")
    private String url;

    @XmlElement(name = "phone")
    private String phone;

    @XmlElement(name = "imgUrl")
    private String imgUrl;

    @XmlElement(name = "gpsX")
    private double gpsX;

    @XmlElement(name = "gpsY")
    private double gpsY;

    @XmlElement(name = "placeUrl")
    private String placeUrl;

    @XmlElement(name = "placeAddr")
    private String placeAddr;

    @XmlElement(name = "placeSeq")
    private int placeSeq;

    @Builder
    public PerforInfo(int seq, String title, String startDate, String endDate, String place, String realmName, String area, String subTitle, String price, String contents1, String contents2, String url, String phone, String imgUrl, double gpsX, double gpsY, String placeUrl, String placeAddr, int placeSeq) {
        this.seq = seq;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.realmName = realmName;
        this.area = area;
        this.subTitle = subTitle;
        this.price = price;
        this.contents1 = contents1;
        this.contents2 = contents2;
        this.url = url;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.placeUrl = placeUrl;
        this.placeAddr = placeAddr;
        this.placeSeq = placeSeq;
    }
}
