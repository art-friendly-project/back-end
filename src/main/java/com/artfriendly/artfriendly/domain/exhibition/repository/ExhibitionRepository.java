package com.artfriendly.artfriendly.domain.exhibition.repository;

import com.artfriendly.artfriendly.domain.exhibition.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    // 복잡한 정렬 방식을 사용하기에 네이티브 쿼리 사용 (Mysql 버전)
    // 복잡한 페이징 저리를 할 때는 countQuery를 해주는게 성능, 정합성 측면에서 유리
    @Query(value = "SELECT e.* FROM exhibition e " +
            "INNER JOIN exhibition_info ei ON ei.exhibition_id = e.id " +
            "WHERE ei.progress_status = :progressStatus AND " +
            "ei.area = :area " +
            "ORDER BY e.temperature DESC, " +
            "CASE " +
            "WHEN ei.title REGEXP '^[가-힣]' THEN 0 " +
            "WHEN ei.title REGEXP '^[A-Za-z]' THEN 1 " +
            "ELSE 2 " +
            "END, " +
            "ei.title ASC",
            countQuery = "SELECT count(*) FROM exhibition e " +
                    "INNER JOIN exhibition_info ei ON ei.exhibition_id = e.id",
            nativeQuery = true)
    Page<Exhibition> findExhibitionByOrderByTemperatureDesc(Pageable pageable,
                                                            @Param("progressStatus") String progressStatus,
                                                            @Param("area") String area);

    @Query(value = "SELECT e.* FROM exhibition e " +
            "INNER JOIN exhibition_info ei ON ei.exhibition_id = e.id " +
            "WHERE ei.progress_status = :progressStatus AND " +
            "ei.area = :area " +
            "ORDER BY ABS(DATEDIFF(ei.start_date, :now)) ASC, " +
            "CASE " +
            "WHEN ei.title REGEXP '^[가-힣]' THEN 0 " +
            "WHEN ei.title REGEXP '^[A-Za-z]' THEN 1 " +
            "ELSE 2 " +
            "END, " +
            "ei.title ASC",
            countQuery = "SELECT count(*) FROM exhibition e " +
                    "INNER JOIN exhibition_info ei ON ei.exhibition_id = e.id",
            nativeQuery = true)
    Page<Exhibition> findExhibitionByOrderByStartDateDesc(Pageable pageable,
                                                            @Param("progressStatus") String progressStatus,
                                                            @Param("area") String area,
                                                            @Param("now") LocalDate now);

    @Query(value = "SELECT e.* FROM exhibition e  " +
            "INNER JOIN exhibition_info ei ON ei.exhibition_id = e.id " +
            "WHERE ei.progress_status = :progressStatus " +
            "ORDER BY e.temperature DESC, ABS(DATEDIFF(ei.start_date, :now)) ASC " +
            "LIMIT 10",
             nativeQuery = true)
    List<Exhibition> findTop10ByTemperature(@Param("progressStatus") String progressStatus, @Param("now") LocalDate now);

    @Query(value = "SELECT e.* FROM exhibition e " +
            "INNER JOIN exhibition_info ei ON ei.exhibition_id = e.id " +
            "WHERE ei.progress_status = :progressStatus " +
            "ORDER BY ABS(DATEDIFF(ei.end_date, :now)) ASC, e.temperature DESC " +
            "LIMIT 3",
            nativeQuery = true)
    List<Exhibition> findTop3ByEndDate(@Param("progressStatus") String progressStatus, @Param("now") LocalDate now);

    @Query(value = "SELECT e FROM  Exhibition e " +
            "JOIN e.exhibitionLikeList el " +
            "WHERE el.member.id = :memberId " +
            "ORDER BY el.lastModifiedTime DESC ")
    Page<Exhibition> findExhibitionByMemberIdOrderByLastModifiedTime(Pageable pageable, @Param("memberId") long memberId);
}
