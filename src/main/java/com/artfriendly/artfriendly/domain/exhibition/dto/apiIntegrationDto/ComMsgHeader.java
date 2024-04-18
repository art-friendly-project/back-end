package com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@XmlAccessorType(XmlAccessType.FIELD)
public class ComMsgHeader {
    @XmlElement(name = "ResponseTime")
    private String responseTime;

    @XmlElement(name = "SuccessYN")
    private String successYN;

    @Builder
    public ComMsgHeader(String responseTime, String successYN) {
        this.responseTime = responseTime;
        this.successYN = successYN;
    }
}
