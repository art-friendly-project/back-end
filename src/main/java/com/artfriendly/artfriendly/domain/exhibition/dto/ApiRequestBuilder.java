package com.artfriendly.artfriendly.domain.exhibition.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiRequestBuilder {
    private String realmCode;
    private String startDate;
    private String endDate;
    private int cPage;
    private int rows;
    private String sido;
    private String gugun;
    private String place;
    private Double gpsxfrom;
    private Double gpsyform;
    private Double gpsxto;
    private Double gpsyto;
    private String keyword;
    private int sortStdr;

    @Builder
    public ApiRequestBuilder(String realmCode, String startDate, String endDate, int cPage, int rows, String sido, String gugun, String place, Double gpsxfrom, Double gpsyform, Double gpsxto, Double gpsyto, String keyword, int sortStdr) {
        this.realmCode = realmCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cPage = cPage;
        this.rows = rows;
        this.sido = sido;
        this.gugun = gugun;
        this.place = place;
        this.gpsxfrom = gpsxfrom;
        this.gpsyform = gpsyform;
        this.gpsxto = gpsxto;
        this.gpsyto = gpsyto;
        this.keyword = keyword;
        this.sortStdr = sortStdr;
    }
}
