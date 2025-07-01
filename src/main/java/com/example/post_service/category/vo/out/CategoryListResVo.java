package com.example.post_service.category.vo.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 리스트 응답")
public class CategoryListResVo {

    @Schema(description = "카테고리 리스트 ID", example = "1")
    private Long id;
    
    @Schema(description = "메인 카테고리 ID", example = "1")
    private Long mainCategoryId;
    
    @Schema(description = "메인 카테고리 이름", example = "프로그래밍")
    private String mainCategoryName;
    
    @Schema(description = "서브 카테고리 ID", example = "10")
    private Long subCategoryId;
    
    @Schema(description = "서브 카테고리 이름", example = "Spring Boot")
    private String subCategoryName;
    
    @Schema(description = "서브 카테고리 색상 (HEX 코드)", example = "#FF6B6B")
    private String subCategoryColor;

    @Builder
    public CategoryListResVo(
            Long id,
            Long mainCategoryId,
            String mainCategoryName,
            Long subCategoryId,
            String subCategoryName,
            String subCategoryColor
    ) {
        this.id = id;
        this.mainCategoryId = mainCategoryId;
        this.mainCategoryName = mainCategoryName;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.subCategoryColor = subCategoryColor;
    }
}
