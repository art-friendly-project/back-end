package com.artfriendly.artfriendly.domain.festival.entity;

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
public class FestivalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    @NotNull
    private int seq;

    @Column
    @NotBlank
    private String title;

    @Column(length = 1000)
    private String description ;

    @Column
    private String organizer;

    @Column(length = 500)
    private String homepageUrl;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private String place;

    @Column
    private String area;

    @Column(length = 1000)
    private String imageUrl;

    @Column
    private double gpsX;

    @Column
    private double gpsY;

    @Column
    private String phone;

    @Column
    private String price;

    @Column
    private String placeAddr;

    @Column(nullable = false)
    @NotNull
    private String progressStatus;

    @OneToOne
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @Builder
    public FestivalInfo(Long id, int seq, String title, String description, String homepageUrl, LocalDate startDate, LocalDate endDate, String place, String organizer, String area, String imageUrl, double gpsX, double gpsY, String phone, String price, String placeAddr, String progressStatus) {
        this.id = id;
        this.seq = seq;
        this.title = title;
        this.description = description;
        this.homepageUrl = homepageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.organizer = organizer;
        this.area = area;
        this.imageUrl = imageUrl;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.phone = phone;
        this.price = price;
        this.placeAddr = placeAddr;
        this.progressStatus = progressStatus;
    }
}
