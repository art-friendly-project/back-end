package com.artfriendly.artfriendly.domain.exhibition.service;

import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;

import java.util.Optional;

public interface ExhibitionInfoService {
    Optional<ExhibitionInfo> findOptionalExhibition(int seq);
}
