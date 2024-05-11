package com.artfriendly.artfriendly.domain.exhibition.service;

import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;
import com.artfriendly.artfriendly.domain.exhibition.repository.ExhibitionInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExhibitionInfoServiceImpl implements ExhibitionInfoService{
    private final ExhibitionInfoRepository exhibitionInfoRepository;
    @Override
    public Optional<ExhibitionInfo> findOptionalExhibition(int seq) {
        return exhibitionInfoRepository.findExhibitionBySeq(seq);
    }

    @Override
    @Transactional
    public void updateExhibitionInfoProgress(LocalDate now) {
        List<ExhibitionInfo> updateExhibitionInfo = new ArrayList<>();
        updateExhibitionInfo.addAll(exhibitionInfoRepository.findExhibitionInfoByProgressStatus("scheduled"));
        updateExhibitionInfo.addAll(exhibitionInfoRepository.findExhibitionInfoByProgressStatus("inProgress"));

        for(ExhibitionInfo exhibitionInfo : updateExhibitionInfo) {
            exhibitionInfo.updateProgressStatus();
        }

        exhibitionInfoRepository.saveAll(updateExhibitionInfo);
    }

}
