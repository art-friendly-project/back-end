package com.artfriendly.artfriendly.domain.exhibition.mapper;

import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionDetailsRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionInfoRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionPageRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.apiIntegrationDto.callExhibitionDto.PerforInfo;
import com.artfriendly.artfriendly.domain.exhibition.entity.Exhibition;
import com.artfriendly.artfriendly.domain.exhibition.entity.ExhibitionInfo;
import com.artfriendly.artfriendly.global.utils.LocalDateFormatter;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ExhibitionMapper {
    default ExhibitionInfo perforInfoToExhibitionInfo(PerforInfo perforInfo) {
        if ( perforInfo == null ) {
            return null;
        }

        ExhibitionInfo.ExhibitionInfoBuilder exhibitionInfo = ExhibitionInfo.builder();

        exhibitionInfo.seq( perforInfo.getSeq() );
        exhibitionInfo.title( perforInfo.getTitle() );
        if ( perforInfo.getStartDate() != null ) {
            exhibitionInfo.startDate( LocalDateFormatter.converToLocalDate(perforInfo.getStartDate(), "yyyyMMdd") );
        }
        if ( perforInfo.getEndDate() != null ) {
            exhibitionInfo.endDate( LocalDateFormatter.converToLocalDate(perforInfo.getEndDate(), "yyyyMMdd") );
        }
        exhibitionInfo.place( perforInfo.getPlace() );
        exhibitionInfo.realmName( perforInfo.getRealmName() );
        exhibitionInfo.area( perforInfo.getArea() );
        exhibitionInfo.imageUrl( perforInfo.getImgUrl() );
        exhibitionInfo.gpsX( perforInfo.getGpsX() );
        exhibitionInfo.gpsY( perforInfo.getGpsY() );
        exhibitionInfo.ticketingUrl( perforInfo.getUrl() );
        exhibitionInfo.phone( perforInfo.getPhone() );
        exhibitionInfo.price( perforInfo.getPrice() );
        exhibitionInfo.placeAddr( perforInfo.getPlaceAddr() );

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

    default ExhibitionDetailsRspDto exhibitionToExhibitionDetailsRspDto(Exhibition exhibition, String checkTemperature, boolean isLike) {
        return new ExhibitionDetailsRspDto(
                exhibition.getId(),
                exhibition.getTemperature(),
                checkTemperature,
                isLike,
                exhibitionInfoToExhibitionInfoRspDto(exhibition.getExhibitionInfo())
        );
    }

    default Page<ExhibitionPageRspDto> exhibitionPageToExhibitionPageRspDto(Page<Exhibition> exhibitionPage, long memberId) {
        return exhibitionPage.map(exhibition -> new ExhibitionPageRspDto(
                exhibition.getId(),
                exhibition.getExhibitionInfo().getTitle(),
                exhibition.getTemperature(),
                exhibition.getExhibitionInfo().getStartDate(),
                exhibition.getExhibitionInfo().getEndDate(),
                exhibition.getExhibitionInfo().getArea(),
                exhibition.getExhibitionLikeList().stream().anyMatch(exhibitionLike -> exhibitionLike.getMember().getId() == memberId)
        ));
    }

    ExhibitionInfoRspDto exhibitionInfoToExhibitionInfoRspDto(ExhibitionInfo exhibitionInfo);
}
