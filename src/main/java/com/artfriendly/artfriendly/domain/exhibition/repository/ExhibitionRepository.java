package com.artfriendly.artfriendly.domain.exhibition.repository;

import com.artfriendly.artfriendly.domain.exhibition.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    // 복잡한 정렬 방식을 사용하기에 네이티브 쿼리 사용 (Mysql 버전)
    // 복잡한 페이징 저리를 할 때는 countQuery를 해주는게 성능, 정합성 측면에서 유리
    @Query(value = "SELECT e.* FROM Exhibition e " +
            "INNER JOIN Exhibition_Info ei ON ei.exhibition_id = e.id " +
            "ORDER BY e.temperature DESC, " +
            "CASE " +
            "WHEN ei.title REGEXP '^[가-힣]' THEN 0 " +
            "WHEN ei.title REGEXP '^[A-Za-z]' THEN 1 " +
            "ELSE 2 " +
            "END, " +
            "ei.title ASC",
            countQuery = "SELECT count(*) FROM Exhibition e " +
                    "INNER JOIN Exhibition_Info ei ON ei.exhibition_id = e.id",
            nativeQuery = true)
    Page<Exhibition> findExhibitionByOrderByTemperatureDesc(Pageable pageable);
}
