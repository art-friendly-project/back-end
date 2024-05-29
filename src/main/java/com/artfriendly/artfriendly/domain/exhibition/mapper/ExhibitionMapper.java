package com.artfriendly.artfriendly.domain.exhibition.mapper;

import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionDetailsRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionInfoRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionRankRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto.PerforInfo;
import com.artfriendly.artfriendly.domain.exhibition.entity.Exhibition;
import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;
import com.artfriendly.artfriendly.global.utils.LocalDateFormatter;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ExhibitionMapper {
    default ExhibitionInfo perforInfoToExhibitionInfo(PerforInfo perforInfo) {
        if ( perforInfo == null ) {
            return null;
        }

        LocalDate startDate = LocalDateFormatter.converToLocalDate(perforInfo.getStartDate(), "yyyyMMdd");
        LocalDate endDate =  LocalDateFormatter.converToLocalDate(perforInfo.getEndDate(), "yyyyMMdd");

        ExhibitionInfo.ExhibitionInfoBuilder exhibitionInfo = ExhibitionInfo.builder();

        exhibitionInfo.seq( perforInfo.getSeq() );
        exhibitionInfo.title( perforInfo.getTitle() );

        if ( perforInfo.getStartDate() != null ) {
            exhibitionInfo.startDate( startDate );
        }
        if ( perforInfo.getEndDate() != null ) {
            exhibitionInfo.endDate( endDate );
        }
        exhibitionInfo.place( perforInfo.getPlace() );
        exhibitionInfo.realmName( perforInfo.getRealmName() );
        exhibitionInfo.area( perforInfo.getArea().equals("경기") || perforInfo.getArea().equals("인천") ? "경기인천" : perforInfo.getArea() );
        exhibitionInfo.imageUrl( perforInfo.getImgUrl() );
        exhibitionInfo.gpsX( perforInfo.getGpsX() );
        exhibitionInfo.gpsY( perforInfo.getGpsY() );
        exhibitionInfo.ticketingUrl( perforInfo.getUrl() );
        exhibitionInfo.phone( perforInfo.getPhone() );
        exhibitionInfo.price( perforInfo.getPrice() );
        exhibitionInfo.placeAddr( perforInfo.getPlaceAddr() );

        LocalDate now = LocalDate.now();

        if(now.isBefore(startDate))
            exhibitionInfo.progressStatus("scheduled");
        else
            exhibitionInfo.progressStatus("inProgress");

        return exhibitionInfo.build();
    }

    default List<ExhibitionInfo> perforInfosToExhibitionInfos(List<PerforInfo> perforInfos) {
        if ( perforInfos == null ) {
            return Collections.emptyList();
        }

        List<ExhibitionInfo> list = new ArrayList<>(perforInfos.size());
        for ( PerforInfo perforInfo : perforInfos ) {
            list.add( perforInfoToExhibitionInfo( perforInfo ) );
        }

        return list;
    }

    default ExhibitionDetailsRspDto exhibitionToExhibitionDetailsRspDto(Exhibition exhibition, String checkTemperature, boolean isLike, boolean hasDambyeolagBeenWritten) {
        return new ExhibitionDetailsRspDto(
                exhibition.getId(),
                exhibition.getTemperature(),
                checkTemperature,
                isLike,
                hasDambyeolagBeenWritten,
                exhibitionInfoToExhibitionInfoRspDto(exhibition.getExhibitionInfo())
        );
    }

    default Page<ExhibitionRspDto> exhibitionPageToExhibitionRspDto(Page<Exhibition> exhibitionPage, long memberId) {
        return exhibitionPage.map(exhibition -> new ExhibitionRspDto(
                exhibition.getId(),
                exhibition.getExhibitionInfo().getTitle(),
                exhibition.getExhibitionInfo().getImageUrl(),
                exhibition.getTemperature(),
                exhibition.getExhibitionInfo().getStartDate(),
                exhibition.getExhibitionInfo().getEndDate(),
                exhibition.getExhibitionInfo().getArea(),
                exhibition.getExhibitionLikeList().stream().anyMatch(exhibitionLike -> exhibitionLike.getMember().getId() == memberId)
        ));
    }

    default List<ExhibitionRspDto> exhibitionsToExhibitionRspDtos(List<Exhibition> exhibitions, long memberId) {
        return exhibitions.stream().map(exhibition -> new ExhibitionRspDto(
                exhibition.getId(),
                exhibition.getExhibitionInfo().getTitle(),
                exhibition.getExhibitionInfo().getImageUrl(),
                exhibition.getTemperature(),
                exhibition.getExhibitionInfo().getStartDate(),
                exhibition.getExhibitionInfo().getEndDate(),
                exhibition.getExhibitionInfo().getArea(),
                exhibition.getExhibitionLikeList().stream().anyMatch(exhibitionLike -> exhibitionLike.getMember().getId() == memberId)
        )).toList();
    }

    default ExhibitionRankRspDto exhibitionToExhibitionRankRspDto(Exhibition exhibition, int rank, String rankShift) {
        return new ExhibitionRankRspDto(
                exhibition.getId(),
                rank,
                exhibition.getExhibitionInfo().getTitle(),
                exhibition.getExhibitionInfo().getImageUrl(),
                exhibition.getExhibitionInfo().getStartDate(),
                exhibition.getExhibitionInfo().getEndDate(),
                exhibition.getExhibitionInfo().getPlace(),
                exhibition.getExhibitionInfo().getArea(),
                rankShift
        );
    }

    ExhibitionInfoRspDto exhibitionInfoToExhibitionInfoRspDto(ExhibitionInfo exhibitionInfo);
}
