package com.example.post_service.category.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "메인 카테고리 생성/수정 요청")
public class MainCategoryReqVo {
    
    @Schema(description = "카테고리 이름", example = "프로그래밍", maxLength = 100, required = true)
    private String name;
    
    @Schema(description = "아이콘 URL", example = "https://example.com/icons/programming.png", required = true)
    private String iconUrl;
    
    @Schema(description = "아이콘 대체 텍스트", example = "프로그래밍 아이콘", maxLength = 200, required = true)
    private String alt;

    @Builder
    public MainCategoryReqVo(
            String name,
            String iconUrl,
            String alt
    ) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.alt = alt;
    }
}
