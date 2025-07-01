package com.example.post_service.category.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "서브 카테고리 생성/수정 요청")
public class SubCategoryReqVo {
    
    @Schema(description = "카테고리 이름", example = "Spring Boot", maxLength = 100, required = true)
    private String name;
    
    @Schema(description = "카테고리 색상 (HEX 코드)", example = "#FF6B6B", pattern = "^#[0-9A-Fa-f]{6}$", required = true)
    private String color;

    @Builder
    public SubCategoryReqVo(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
