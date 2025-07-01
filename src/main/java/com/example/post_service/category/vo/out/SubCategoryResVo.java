package com.example.post_service.category.vo.out;

import com.example.post_service.category.dto.out.SubCategoryResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "서브 카테고리 응답")
public class SubCategoryResVo {
    
    @Schema(description = "카테고리 ID", example = "10")
    private Long id;
    
    @Schema(description = "카테고리 이름", example = "Spring Boot")
    private String name;
    
    @Schema(description = "카테고리 색상 (HEX 코드)", example = "#FF6B6B")
    private String color;

    @Builder
    public SubCategoryResVo(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public static SubCategoryResVo of(SubCategoryResDto subCategoryResDto) {
        return SubCategoryResVo.builder()
                .id(subCategoryResDto.getId())
                .name(subCategoryResDto.getName())
                .color(subCategoryResDto.getColor())
                .build();
    }
}
