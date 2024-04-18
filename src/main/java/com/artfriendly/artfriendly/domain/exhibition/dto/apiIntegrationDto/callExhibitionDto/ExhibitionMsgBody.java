package com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@XmlAccessorType(XmlAccessType.FIELD)
public class ExhibitionMsgBody {
    @XmlElement(name = "seq")
    private int seq;

    @XmlElement(name ="perforInfo")
    private PerforInfo perforInfo;
}
