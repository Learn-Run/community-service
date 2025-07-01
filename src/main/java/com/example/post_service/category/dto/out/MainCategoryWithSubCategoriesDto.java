package com.example.post_service.category.dto.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MainCategoryWithSubCategoriesDto {
    
    private Long mainCategoryId;
    private String mainCategoryName;
    private String iconUrl;
    private String alt;
    private List<SubCategoryDto> subCategories;
    
    @Getter
    @Builder
    public static class SubCategoryDto {
        private Long subCategoryId;
        private String subCategoryName;
        private String color;
    }
} 