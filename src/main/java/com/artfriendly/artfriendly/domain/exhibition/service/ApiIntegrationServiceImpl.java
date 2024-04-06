package com.artfriendly.artfriendly.domain.exhibition.service;

import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Primary
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ApiIntegrationServiceImpl implements ApiIntegrationService {
    @Value("${exhibition.api.credentials.service-key}")
    private String serviceKey;

    @Value("${exhibition.api.duration}")
    private String durationApi;

    @Override
    public String integrateExhibitionInfo(String reqStartDate, String reqEndDate) {
        String encodedServiceKey = encodeValue(serviceKey);
        String startDate = encodeValue(reqStartDate);
        String endDate = encodeValue(reqEndDate);

        String url = durationApi;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("from", startDate)
                .queryParam("to", endDate)
                .queryParam("cPage",encodeValue("1"))
                .queryParam("rows", encodeValue("50"))
                .queryParam("place", encodeValue(""))
                .queryParam("gpsxfrom", encodeValue(""))
                .queryParam("gpsyfrom", encodeValue(""))
                .queryParam("gpsxto", encodeValue(""))
                .queryParam("gpsyto", encodeValue(""))
                .queryParam("keyword", encodeValue(""))
                .queryParam("sortStdr", encodeValue("1"))
                .queryParam("serviceKey", serviceKey);

        RestTemplate restTemplate = new RestTemplate();
        log.info(String.valueOf(encodedServiceKey.equals(serviceKey)));
        return restTemplate.getForObject(builder.toUriString(), String.class);
    }

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_ENCODING_DATA);
        }
    }
}
