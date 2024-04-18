package com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@XmlAccessorType(XmlAccessType.FIELD)
public class PerforList {
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

    @XmlElement(name = "thumbnail")
    private String thumbnail;

    @XmlElement(name = "gpsX")
    private double gpsX;

    @XmlElement(name = "gpsY")
    private double gpsY;

    @Builder
    public PerforList(int seq, String title, String startDate, String endDate, String place, String realmName, String area, String thumbnail, double gpsX, double gpsY) {
        this.seq = seq;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.realmName = realmName;
        this.area = area;
        this.thumbnail = thumbnail;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
    }
}
