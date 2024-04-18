package com.artfriendly.artfriendly.domain.exhibition.service;

import com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto.ExhibitionsApiRspData;
import com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto.ExhibitionApiRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ApiRequestBuilder;


import java.time.LocalDate;

public interface ApiIntegrationService {
    ExhibitionsApiRspData callExhibitionApi(ApiRequestBuilder req);
    ExhibitionApiRspDto callExhibitionDetailsApi(int seq);
    void integrateExhibitionInfo(LocalDate now, int duration, String realmCode);
}
