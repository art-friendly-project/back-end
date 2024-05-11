package com.artfriendly.artfriendly.domain.exhibition.repository;

import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExhibitionInfoRepository extends JpaRepository<ExhibitionInfo, Long> {
    Optional<ExhibitionInfo> findExhibitionBySeq(int seq);
    List<ExhibitionInfo> findExhibitionInfoByProgressStatus(String progressStatus);
}
