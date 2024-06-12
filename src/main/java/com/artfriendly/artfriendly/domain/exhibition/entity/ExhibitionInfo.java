package com.artfriendly.artfriendly.domain.exhibition.entity;

import com.artfriendly.artfriendly.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExhibitionInfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    @NotNull
    private int seq;

    @Column
    @NotBlank
    private String title;

    @Column(length = 500)
    private String detailInfoUrl;

    @Column
    @NotNull
    private LocalDate startDate;

    @Column
    @NotNull
    private LocalDate endDate;

    @Column
    @NotBlank
    private String place;

    @Column
    @NotBlank
    private String realmName;

    @Column
    @NotBlank
    private String area;

    @Column(length = 1000)
    @NotBlank
    private String imageUrl;

    @Column
    @NotNull
    private double gpsX;

    @Column
    @NotNull
    private double gpsY;

    @Column(length = 1500)
    @NotNull
    private String ticketingUrl;

    @Column
    @NotBlank
    private String phone;

    @Column(nullable = false)
    @NotBlank
    private String price;

    @Column
    @NotNull
    private String placeAddr;

    @Column(nullable = false)
    @NotNull
    private String progressStatus;

    @OneToOne
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    public ExhibitionInfo(Long id, int seq, String title, String detailInfoUrl, LocalDate startDate, LocalDate endDate, String place, String realmName, String area, String imageUrl, double gpsX, double gpsY, String ticketingUrl, String phone, String price, String placeAddr, String progressStatus) {
        this.id = id;
        this.seq = seq;
        this.title = title;
        this.detailInfoUrl = detailInfoUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.realmName = realmName;
        this.area = area;
        this.imageUrl = imageUrl;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.ticketingUrl = ticketingUrl;
        this.phone = phone;
        this.price = price;
        this.placeAddr = placeAddr;
        this.progressStatus = progressStatus;
    }

    public void updateForm(ExhibitionInfo updateExhibitionInfo) {
        this.title = updateExhibitionInfo.getTitle();
        this.startDate = updateExhibitionInfo.getStartDate();
        this.endDate = updateExhibitionInfo.getEndDate();
        this.place = updateExhibitionInfo.getPlace();
        this.realmName = updateExhibitionInfo.getRealmName();
        this.area = updateExhibitionInfo.getArea();
        this.imageUrl = updateExhibitionInfo.getImageUrl();
        this.gpsX = updateExhibitionInfo.getGpsX();
        this.gpsY = updateExhibitionInfo.getGpsY();
        this.ticketingUrl = updateExhibitionInfo.getTicketingUrl();
        this.phone = updateExhibitionInfo.getPhone();
        this.price = updateExhibitionInfo.getPrice();
        this.placeAddr = updateExhibitionInfo.getPlaceAddr();
        this.progressStatus = updateExhibitionInfo.getProgressStatus();
    }

    public void updateProgressStatus() {
        LocalDate now = LocalDate.now();
        // 종료 날짜가 지난 경우
        if (now.isAfter(this.getEndDate())) {
            this.progressStatus = "ended";
        }
        // 현재 날짜가 시작 날짜와 같거나 이후이며, 종료 날짜 이전인 경우
        else if (!now.isBefore(this.getStartDate()) && now.isBefore(this.getEndDate())) {
            this.progressStatus = "inProgress";
        }
        // 시작 날짜가 아직 오지 않은 경우
        else if (now.isBefore(this.getStartDate())) {
            this.progressStatus = "scheduled";
        }
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }

}
