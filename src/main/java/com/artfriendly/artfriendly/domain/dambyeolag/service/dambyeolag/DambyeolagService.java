package com.artfriendly.artfriendly.domain.dambyeolag.service.dambyeolag;

import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.DambyeolagDetailsRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.DambyeolagReqDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.DambyeolagRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import org.springframework.data.domain.Page;

public interface DambyeolagService {
    DambyeolagDetailsRspDto getDetailsDambyeolag(long memberId, long dambyeolagId);
    Page<DambyeolagRspDto> getDambyeolagPageOrderByStickerDesc(int page, long exhibitionId);
    void createDambyeolag(DambyeolagReqDto dambyeolagReqDto, long memberId);
    void deleteDambyeolag(long memberId, long dambyeolagId);
    Dambyeolag findById(long dambyeolagId);
    void deleteBookmark(long memberId, long dambyeolagBookmarkId);
    void addBookmark(long memberId, long dambyeolagId);

}
