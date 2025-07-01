package com.example.post_service.category.vo.out;

import com.example.post_service.category.dto.out.MainCategoryResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "메인 카테고리 응답")
public class MainCategoryResVo {
    
    @Schema(description = "카테고리 ID", example = "1")
    private Long id;
    
    @Schema(description = "카테고리 이름", example = "프로그래밍")
    private String name;
    
    @Schema(description = "아이콘 URL", example = "https://example.com/icons/programming.png")
    private String iconUrl;
    
    @Schema(description = "아이콘 대체 텍스트", example = "프로그래밍 아이콘")
    private String alt;

    @Builder
    public MainCategoryResVo(Long id, String name, String iconUrl, String alt) {
        this.id = id;
        this.name = name;
        this.iconUrl = iconUrl;
        this.alt = alt;
    }

    public static MainCategoryResVo of(MainCategoryResDto mainCategoryResDto) {
        return MainCategoryResVo.builder()
                .id(mainCategoryResDto.getId())
                .name(mainCategoryResDto.getName())
                .iconUrl(mainCategoryResDto.getIconUrl())
                .alt(mainCategoryResDto.getAlt())
                .build();
    }
}
