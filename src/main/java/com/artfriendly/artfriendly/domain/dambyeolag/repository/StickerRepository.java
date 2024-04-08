package com.artfriendly.artfriendly.domain.dambyeolag.repository;

import com.artfriendly.artfriendly.domain.dambyeolag.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
    @Query("SELECT s FROM Sticker s " +
            "WHERE s.dambyeolag.id = :dambyeolagId")
    List<Sticker> findStickerByDambyeolagId(@Param("dambyeolagId") long dambyeolagId);

}
