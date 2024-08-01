package com.artfriendly.artfriendly.domain.exhibition.service;

import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;
import com.artfriendly.artfriendly.domain.exhibition.repository.ExhibitionInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
