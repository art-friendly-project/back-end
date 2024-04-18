package com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto;

import com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.ComMsgHeader;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExhibitionsApiRspData {
    @XmlElement(name = "comMsgHeader")
    private ComMsgHeader comMsgHeader;

    @XmlElement(name = "msgBody")
    private ExhibitionsMsgBody exhibitionsMsgBody;

    @Builder
    public ExhibitionsApiRspData(ComMsgHeader comMsgHeader, ExhibitionsMsgBody exhibitionsMsgBody) {
        this.comMsgHeader = comMsgHeader;
        this.exhibitionsMsgBody = exhibitionsMsgBody;
    }
}

