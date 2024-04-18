package com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto;

import com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.ComMsgHeader;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExhibitionApiRspDto {
    @XmlElement(name = "comMsgHeader")
    private ComMsgHeader comMsgHeader;

    @XmlElement(name = "msgBody")
    private ExhibitionMsgBody msgBody;

    @Builder
    public ExhibitionApiRspDto(ComMsgHeader comMsgHeader, ExhibitionMsgBody msgBody) {
        this.comMsgHeader = comMsgHeader;
        this.msgBody = msgBody;
    }
}
