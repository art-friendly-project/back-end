package com.artfriendly.artfriendly.domain.exhibition.service;

import com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto.ExhibitionApiRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto.PerforList;
import com.artfriendly.artfriendly.domain.exhibition.dto.ApiRequestBuilder;
import com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionsDto.ExhibitionsApiRspData;
import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;
import com.artfriendly.artfriendly.domain.exhibition.mapper.ExhibitionMapper;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import com.artfriendly.artfriendly.global.utils.ErrorResponder;
import com.artfriendly.artfriendly.global.utils.LocalDateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Primary
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApiIntegrationServiceImpl implements ApiIntegrationService {
    private final ExhibitionMapper exhibitionMapper;
    private final ExhibitionInfoService exhibitionInfoService;
    private final ExhibitionService exhibitionService;

    @Value("${exhibition.api.credentials.service-key}")
    private String serviceKey;


    @Value("${exhibition.api.details}")
    private String detailsApiUrl;

    @Value("${exhibition.api.realm}")
    private String realmApiUrl;

    @Override
    public ExhibitionsApiRspData callExhibitionApi(ApiRequestBuilder req) {

        String url = realmApiUrl;
        UriComponentsBuilder builder = UriComponentsBuilder .fromHttpUrl(url)
                .queryParam("realmCode", encodeValue(req.getRealmCode()))
                .queryParam("cPage",encodeValue(String.valueOf(req.getCPage())))
                .queryParam("rows", encodeValue(String.valueOf(req.getRows())))
                .queryParam("from", encodeValue(req.getStartDate()))
                .queryParam("to", encodeValue(req.getEndDate()))
                .queryParam("sido", encodeValue(req.getSido() == null ? "" : req.getSido()))
                .queryParam("gugun", req.getGugun() == null ? "" : req.getGugun())
                .queryParam("place", encodeValue(req.getPlace() == null ? "" : req.getPlace()))
                .queryParam("gpsxfrom", encodeValue(String.valueOf(req.getGpsxfrom() == null ? "" : req.getGpsxfrom())))
                .queryParam("gpsyfrom", encodeValue(String.valueOf(req.getGpsyform() == null ? "" : req.getGpsyform())))
                .queryParam("gpsxto", encodeValue(String.valueOf(req.getGpsxto() == null ? "" : req.getGpsxto())))
                .queryParam("gpsyto", encodeValue(String.valueOf(req.getGpsyto() == null ? "" : req.getGpsyto())))
                .queryParam("keyword", encodeValue(req.getKeyword() == null ? "" : req.getKeyword()))
                .queryParam("sortStdr", encodeValue(String.valueOf(req.getSortStdr())))
                .queryParam("serviceKey", serviceKey);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // XML 파싱시 유니코드 문자가 있어서 UTF-8로 응답을 읽는다.
        ExhibitionsApiRspData exhibitionsApiRspData = restTemplate.getForObject(builder.build(true).toUri(), ExhibitionsApiRspData.class);

        if(exhibitionsApiRspData.getComMsgHeader().getSuccessYN().equals("N"))
            throw new BusinessException(ErrorCode.API_CALL_FAILED);

        return exhibitionsApiRspData;
    }

    @Override
    public ExhibitionApiRspDto callExhibitionDetailsApi(int seq) {
        String url = detailsApiUrl;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("seq", encodeValue(String.valueOf(seq)))
                .queryParam("serviceKey", serviceKey);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // XML 파싱시 유니코드 문자가 있어서 UTF-8로 응답을 읽는다.
        ExhibitionApiRspDto exhibitionApiRspDto = restTemplate.getForObject(builder.build(true).toUri(), ExhibitionApiRspDto.class);

        if(exhibitionApiRspDto.getComMsgHeader().getSuccessYN().equals("N"))
            throw new BusinessException(ErrorCode.API_CALL_FAILED);

        return exhibitionApiRspDto;
    }

    @Transactional
    @Override
    public void integrateExhibitionInfo(LocalDate now, int duration, String realmCode) {
        // n개월 안에 끝나는 realm코드 분야의 perforList 정보를 가져온다
        List<PerforList> allPerforLists = getPerforLists(now, duration, realmCode);
        // 가져온 정보를 Thread를 통해 새부 전시 정보를 데이터베이스에 수정, 저장한다.
        integrationToThreads(allPerforLists);
    }

    private List<PerforList> getPerforLists(LocalDate now, int duration, String realmCode) {
        List<PerforList> perforLists;
        List<PerforList> allPerforLists = new ArrayList<>();

        // 지금으로 부터 duration 개월 안에 끝나는 전시회.
        String startDate = LocalDateFormatter.convertToString(now, "yyyyMMdd");
        String endDate = LocalDateFormatter.convertToString(now.plusMonths(duration), "yyyyMMdd");
        int cPage = 1;

        // 전체 리스트의 seq를 받아온다
        while(true) {
            ApiRequestBuilder apiRequestBuilder = ApiRequestBuilder.builder()
                    .realmCode(realmCode)
                    .startDate(startDate)
                    .endDate(endDate)
                    .rows(100) // 한 페이지에 최대 100개의 데이터를 받는다.
                    .cPage(cPage) // 시작 페이지는 1페이지.
                    .sortStdr(1) // 등록일 기준으로 정렬
                    .build();

            ExhibitionsApiRspData responseData = callExhibitionApi(apiRequestBuilder);
            perforLists = responseData.getExhibitionsMsgBody().getPerforList();

            // perforLists가 null 이면 api 호출 중지
            if(perforLists == null)
                break;

            allPerforLists.addAll(perforLists);
            cPage = responseData.getExhibitionsMsgBody().getCPage() + 1;
        }
        return allPerforLists;
    }

    private void integrationToThreads(List<PerforList> allPerforLists) {
        int totalTasks = allPerforLists.size();
        int numThreads = Math.min(10, totalTasks);

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        List<Future<Boolean>> futures = new ArrayList<>();

        int tasksPerThread = totalTasks / numThreads;
        int remainingTasks = totalTasks % numThreads;

        int startIndex = 0;
        for (int i = 0; i < numThreads; i++) {
            int endIndex = startIndex + tasksPerThread - 1 + (i < remainingTasks ? 1 : 0);

            int finalStartIndex = startIndex;
            int finalEndIndex = endIndex;

            Future<Boolean> future = executorService.submit(() -> integrateExhibition(finalStartIndex, finalEndIndex, allPerforLists));
            futures.add(future);

            startIndex = endIndex + 1;
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                log.warn("작업 시간이 30초를 넘어가서 연동 작업을 중지합니다.");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 다른 스레드 모두 중지
            // executorService를 즉시 종료시키기 위해 shutdownNow()를 호출한다
            executorService.shutdownNow();
        }

        // 작업의 성공 여부 확인
        for (Future<Boolean> future : futures) {
            try {
                if (Boolean.FALSE.equals(future.get())) {
                    log.warn("Integration tasks failed.");
                    break;
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                log.warn("future.get() Error");
                ErrorResponder.printLog(e);
            }
        }
    }

    private Boolean integrateExhibition(int start, int end, List<PerforList> perforLists) {
        try {
            List<ExhibitionInfo> exhibitionInfoList = new CopyOnWriteArrayList<>(); // 스레드에서 사용되는 리스트는 해당 리스트나 Collections.synchronizedList를 사용한다.

            for (int i = start; i <= end; i++) {
                ExhibitionInfo exhibitionInfo;
                ExhibitionApiRspDto exhibitionApiRspDto = callExhibitionDetailsApi(perforLists.get(i).getSeq());
                Optional<ExhibitionInfo> optionalExhibition = exhibitionInfoService.findOptionalExhibition(perforLists.get(i).getSeq());

                if (optionalExhibition.isPresent()) {
                    exhibitionInfo = optionalExhibition.get();
                    exhibitionInfo.updateForm(exhibitionMapper.perforInfoToExhibitionInfo(exhibitionApiRspDto.getMsgBody().getPerforInfo()));
                } else {
                    exhibitionInfo = exhibitionMapper.perforInfoToExhibitionInfo(exhibitionApiRspDto.getMsgBody().getPerforInfo());
                }
                exhibitionInfoList.add(exhibitionInfo);
            }

            exhibitionService.createExhibitionList(exhibitionInfoList);
            return true;
        } catch (Exception e) {
            log.warn("api 호출 후 연동 작업에 실패했습니다.");
            ErrorResponder.printLog(e);
            return false;
        }
    }

    private String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
