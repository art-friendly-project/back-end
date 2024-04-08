package com.artfriendly.artfriendly.domain.dambyeolag.mapper;

import com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker.StickerReqDto;
import com.artfriendly.artfriendly.domain.dambyeolag.dto.sticker.StickerRspDto;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Dambyeolag;
import com.artfriendly.artfriendly.domain.dambyeolag.entity.Sticker;
import com.artfriendly.artfriendly.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StickerMapper {
    default Sticker stickerReqDtoToSticker(StickerReqDto stickerReqDto, Member member, Dambyeolag dambyeolag) {
        return Sticker.builder()
                .xCoordinate(stickerReqDto.xCoordinate())
                .yCoordinate(stickerReqDto.yCoordinate())
                .body(stickerReqDto.body())
                .member(member)
                .dambyeolag(dambyeolag)
                .build();
    }

    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "dambyeolag.id", target = "dambyeolagId")
    StickerRspDto stickerToStickerRspDto(Sticker sticker);

    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "dambyeolag.id", target = "dambyeolagId")
    List<StickerRspDto> stickersToStickerRspDtos(List<Sticker> stickers);
}
