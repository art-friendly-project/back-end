package com.artfriendly.artfriendly.domain.mbti.mapper;

import com.artfriendly.artfriendly.domain.mbti.dto.MbtiReqDto;
import com.artfriendly.artfriendly.domain.mbti.dto.MbtiRspDto;
import com.artfriendly.artfriendly.domain.mbti.dto.MbtiSimpleRspDto;
import com.artfriendly.artfriendly.domain.mbti.entity.Mbti;
import com.artfriendly.artfriendly.domain.mbti.service.MbtiService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MbtiService.class})
public interface MbtiMapper {

    default MbtiRspDto mbtiToMbtiRspDto(Mbti mbti, String percentage) {
        return new MbtiRspDto(
                mbti.getId(),
                mbti.getMbtiType(),
                mbti.getSubTitle(),
                mbti.getTitle(),
                percentage,
                mbti.getBody(),
                mbti.getImageUrl(),
                mbtiToMbtiSimpleRspDto(mbti.getMatchType()),
                mbtiToMbtiSimpleRspDto(mbti.getMissMatchType())
        );
    }

    MbtiSimpleRspDto mbtiToMbtiSimpleRspDto(Mbti mbti);

    default Mbti mbtiReqDtoToMbti(MbtiReqDto mbtiReqDto, Mbti matchType, Mbti missMatchType) {
        return Mbti.builder()
                .mbtiType(mbtiReqDto.mbtiType())
                .subTitle(mbtiReqDto.subTitle())
                .title(mbtiReqDto.title())
                .body(mbtiReqDto.body())
                .imageUrl(mbtiReqDto.imageUrl())
                .matchType(matchType)
                .missMatchType(missMatchType)
                .build();
    }
}
