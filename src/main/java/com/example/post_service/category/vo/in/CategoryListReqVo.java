package com.example.post_service.category.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 리스트 생성 요청")
public class CategoryListReqVo {

    @Schema(description = "메인 카테고리 ID", example = "1", required = true)
    private Long mainCategoryId;
    
    @Schema(description = "서브 카테고리 ID", example = "10", required = true)
    private Long subCategoryId;

    @Builder
    public CategoryListReqVo(
            Long mainCategoryId,
            Long subCategoryId
    ) {
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
    }
}
