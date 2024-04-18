package com.artfriendly.artfriendly.domain.exhibition.controller;

import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionDetailsRspDto;
import com.artfriendly.artfriendly.domain.exhibition.dto.ExhibitionPageRspDto;
import com.artfriendly.artfriendly.domain.exhibition.service.ExhibitionService;
import com.artfriendly.artfriendly.global.api.RspTemplate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {
    private final ExhibitionService exhibitionService;
    @GetMapping
    public RspTemplate<ExhibitionDetailsRspDto> getExhibitionDetails(@AuthenticationPrincipal long memberId,
                                                                     @RequestParam @NotNull long exhibitionId) {
        ExhibitionDetailsRspDto exhibitionDetailsRspDto = exhibitionService.getExhibitionDetailsRpsDtoById(memberId, exhibitionId);
        return new RspTemplate<>(HttpStatus.OK, "전시 id : "+exhibitionId+" 조회", exhibitionDetailsRspDto);
    }

    @GetMapping("/lists")
    public RspTemplate<Page<ExhibitionPageRspDto>> getExhibitionList(@AuthenticationPrincipal long memberId,
                                                                     @RequestParam @Min(0) int page) {
        Page<ExhibitionPageRspDto> exhibitionPageRspDtos = exhibitionService.getExhibitionPageRspDto(memberId, page);
        return new RspTemplate<>(HttpStatus.OK, "전시 "+page+" 페이지 조회", exhibitionPageRspDtos);
    }

    @PostMapping("/likes")
    public RspTemplate<Void> addExhibitionLike(@AuthenticationPrincipal long memberId,
                                               @RequestParam @NotNull long exhibitionId) {
        exhibitionService.addExhibitionLike(memberId, exhibitionId);
        return new RspTemplate<>(HttpStatus.CREATED, "전시 id : "+exhibitionId+" 좋아요 추가");
    }

    @DeleteMapping("/likes")
    public RspTemplate<Void> deleteExhibitionLike(@AuthenticationPrincipal long memberId,
                                                  @RequestParam @NotNull long exhibitionId) {
        exhibitionService.deleteExhibitionLike(memberId, exhibitionId);
        return new RspTemplate<>(HttpStatus.OK, "전시 id : "+exhibitionId+" 좋아요 삭제");
    }

    @PostMapping("/hopes")
    public RspTemplate<Void> addExhibitionHope(@AuthenticationPrincipal long memberId,
                                               @RequestParam @NotNull long exhibitionId, @RequestParam @Min(1) @Max(5) int hopeIndex) {
        exhibitionService.addExhibitionHope(memberId, exhibitionId, hopeIndex);
        return new RspTemplate<>(HttpStatus.CREATED, "전시 id : "+exhibitionId+" 전시 희망 "+hopeIndex+" 추가");
    }

    @PatchMapping("/hopes")
    public RspTemplate<Void> updateExhibitionHope(@AuthenticationPrincipal long memberId,
                                                  @RequestParam @NotNull long exhibitionId, @RequestParam @Min(1) @Max(5) int hopeIndex) {
        exhibitionService.updateExhibitionHope(memberId, exhibitionId, hopeIndex);
        return new RspTemplate<>(HttpStatus.OK, "전시 id : "+exhibitionId+" 전시 희망 "+hopeIndex+" 변경");
    }

    @DeleteMapping("/hopes")
    public RspTemplate<Void> deleteExhibitionHope(@AuthenticationPrincipal long memberId,
                                                    @RequestParam @NotNull long exhibitionId) {
        exhibitionService.deleteExhibitionHope(memberId, exhibitionId);
        return new RspTemplate<>(HttpStatus.OK, "전시 id : "+exhibitionId+" 전시 희망 삭제");
    }

}
