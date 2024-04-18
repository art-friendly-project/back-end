package com.artfriendly.artfriendly.domain.exhibition.entity;

import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exhibition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private Double temperature;

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    List<ExhibitionHope> exhibitionHopeList = new ArrayList<>();

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    List<ExhibitionView> exhibitionViewList = new ArrayList<>();

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    List<ExhibitionLike> exhibitionLikeList = new ArrayList<>();

    @OneToOne(mappedBy = "exhibition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    ExhibitionInfo exhibitionInfo;

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Dambyeolag> dambyeolagList;

    @Builder
    public Exhibition(Long id, List<ExhibitionHope> exhibitionHopeList, List<ExhibitionView> exhibitionViewList, List<ExhibitionLike> exhibitionLikeList, ExhibitionInfo exhibitionInfo) {
        this.id = id;
        this.temperature = 0.0;
        this.exhibitionHopeList = exhibitionHopeList;
        this.exhibitionViewList = exhibitionViewList;
        this.exhibitionLikeList = exhibitionLikeList;
        this.exhibitionInfo = exhibitionInfo;
    }

    public void updateTemperature() {
        Double viewPoint = this.exhibitionViewList.size() * 0.1;
        Double likePoint = this.exhibitionLikeList.size() * 0.5;
        Double hopePoint = this.exhibitionHopeList.stream().mapToDouble(exhibitionHope -> exhibitionHope.getHope().getHopeRating()).sum();

        double totalPoint = viewPoint + likePoint + hopePoint;

        this.temperature = Math.max(totalPoint, 0.0);

    }
}
