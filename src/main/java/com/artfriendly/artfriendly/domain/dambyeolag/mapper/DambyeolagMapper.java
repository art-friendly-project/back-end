package com.artfriendly.artfriendly.domain.dambyeolag.mapper;

import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.DambyeolagDetailsRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.DambyeolagReqDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.dambyeolag.DambyeolagRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import com.artfriendly.artfriendly.domain.exhibition.entity.Exhibition;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import com.artfriendly.artfriendly.domain.member.mapper.MemberMapper;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface DambyeolagMapper {
    default DambyeolagDetailsRspDto dambyeolagToDambyeolagDetailsRspDto(Dambyeolag dambyeolag, boolean isBookmark, boolean isSticker, MemberMapper memberMapper, StickerMapper stickerMapper) {
        return new DambyeolagDetailsRspDto(
                dambyeolag.getId(),
                dambyeolag.getTitle(),
                dambyeolag.getBody(),
                dambyeolag.getLastModifiedTime(),
                memberMapper.memberToMemberResponseDto(dambyeolag.getMember()),
                stickerMapper.stickersToStickerRspDtos(dambyeolag.getStickerList()),
                dambyeolag.getBookmarkList().size(),
                dambyeolag.getStickerList().size(),
                isBookmark,
                isSticker
        );
    }

    default Dambyeolag dambyeolagReqDtoToDambyeolag(DambyeolagReqDto dambyeolagReqDto, Member member, Exhibition exhibition) {
        return Dambyeolag.builder()
                .title(dambyeolagReqDto.title())
                .body(dambyeolagReqDto.body())
                .member(member)
                .exhibition(exhibition)
                .build();
    }

    default Page<DambyeolagRspDto> dambyeolagPageTodambyeolagRspDtoPage(Page<Dambyeolag> dambyeolagPage) {
        return dambyeolagPage.map(dambyeolag -> new DambyeolagRspDto(
                dambyeolag.getId(),
                dambyeolag.getTitle(),
                dambyeolag.getBody()
        ));
    }
}
