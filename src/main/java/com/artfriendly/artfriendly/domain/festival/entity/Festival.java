package com.artfriendly.artfriendly.domain.festival.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Festival {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private Double temperature;

    @OneToOne(mappedBy = "festival", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private FestivalInfo festivalInfo;

    @Builder
    public Festival(Long id, Double temperature, FestivalInfo festivalInfo) {
        this.id = id;
        this.temperature = temperature;
        this.festivalInfo = festivalInfo;
    }
}
