package com.artfriendly.artfriendly.domain.exhibition.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exhibition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private int seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private String realmName;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String gpsX;

    @Column(nullable = false)
    private String gpsY;
}
