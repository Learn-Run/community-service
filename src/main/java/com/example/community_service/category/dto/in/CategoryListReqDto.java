package com.example.community_service.category.dto.in;

import com.example.community_service.category.entity.CategoryList;
import com.example.community_service.category.entity.MainCategory;
import com.example.community_service.category.entity.SubCategory;
import com.example.community_service.category.vo.in.CategoryListReqVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryListReqDto {

    private Integer mainCategoryId;
    private String mainCategoryName;
    private Integer subCategoryId;
    private String subCategoryName;

    @Builder
    public CategoryListReqDto(
            Integer mainCategoryId,
            String mainCategoryName,
            Integer subCategoryId,
            String subCategoryName
    ) {
        this.mainCategoryId = mainCategoryId;
        this.mainCategoryName = mainCategoryName;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
    }

    public static CategoryListReqDto from(CategoryListReqVo vo) {
        return CategoryListReqDto.builder()
                .mainCategoryId(vo.getMainCategoryId())
                .subCategoryId(vo.getSubCategoryId())
                .build();
    }

    public CategoryList of(MainCategory mainCategory, SubCategory subCategory) {
        return CategoryList.builder()
                .mainCategoryId(mainCategory.getId())
                .mainCategoryName(mainCategory.getName())
                .subCategoryId(subCategory.getId())
                .subCategoryName(subCategory.getName())
                .build();
    }
}
