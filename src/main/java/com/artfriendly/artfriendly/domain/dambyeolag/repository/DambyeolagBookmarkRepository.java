package com.artfriendly.artfriendly.domain.dambyeolag.repository;

import com.artfriendly.artfriendly.domain.dambyeolag.entity.DambyeolagBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DambyeolagBookmarkRepository extends JpaRepository<DambyeolagBookmark, Long> {
    @Query("SELECT d FROM DambyeolagBookmark d " +
            "WHERE d.dambyeolag.id = :dambyeolagId")
    List<DambyeolagBookmark> findDambyeolagBookmarkByDambyeolagId(@Param("dambyeolagId") long dambyeolagId);
}
