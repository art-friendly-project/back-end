package com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@XmlAccessorType(XmlAccessType.FIELD)
public class ExhibitionsMsgBody {
    @XmlElement(name = "totalCount")
    private int totalCount;

    @XmlElement(name = "cPage")
    private int cPage;

    @XmlElement(name = "perforList")
    private List<PerforList> perforList;

    @Builder
    public ExhibitionsMsgBody(int totalCount, int cPage, List<PerforList> perforList) {
        this.totalCount = totalCount;
        this.cPage = cPage;
        this.perforList = perforList;
    }
}
