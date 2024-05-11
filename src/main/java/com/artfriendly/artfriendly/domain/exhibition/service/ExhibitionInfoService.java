package com.artfriendly.artfriendly.domain.exhibition.service;

import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

public interface ExhibitionInfoService {
    Optional<ExhibitionInfo> findOptionalExhibition(int seq);

    @Transactional
    void updateExhibitionInfoProgress(LocalDate now);
}
