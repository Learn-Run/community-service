package com.example.post_service.category.vo.out;

import com.example.post_service.category.dto.out.MainCategoryWithSubCategoriesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "메인 카테고리와 서브카테고리 리스트 응답")
public class MainCategoryWithSubCategoriesVo {
    
    @Schema(description = "메인 카테고리 ID", example = "1")
    private Long mainCategoryId;
    
    @Schema(description = "메인 카테고리 이름", example = "프로그래밍")
    private String mainCategoryName;
    
    @Schema(description = "아이콘 URL", example = "https://example.com/icons/programming.png")
    private String iconUrl;
    
    @Schema(description = "아이콘 대체 텍스트", example = "프로그래밍 아이콘")
    private String alt;
    
    @Schema(description = "서브카테고리 리스트")
    private List<SubCategoryVo> subCategories;
    
    @Getter
    @Schema(description = "서브카테고리 정보")
    public static class SubCategoryVo {
        
        @Schema(description = "서브카테고리 ID", example = "10")
        private Long subCategoryId;
        
        @Schema(description = "서브카테고리 이름", example = "Spring Boot")
        private String subCategoryName;
        
        @Schema(description = "서브카테고리 색상 (HEX 코드)", example = "#FF6B6B")
        private String color;
        
        @Builder
        public SubCategoryVo(Long subCategoryId, String subCategoryName, String color) {
            this.subCategoryId = subCategoryId;
            this.subCategoryName = subCategoryName;
            this.color = color;
        }
    }
    
    @Builder
    public MainCategoryWithSubCategoriesVo(Long mainCategoryId, String mainCategoryName, String iconUrl, String alt, List<SubCategoryVo> subCategories) {
        this.mainCategoryId = mainCategoryId;
        this.mainCategoryName = mainCategoryName;
        this.iconUrl = iconUrl;
        this.alt = alt;
        this.subCategories = subCategories;
    }
    
    public static MainCategoryWithSubCategoriesVo from(MainCategoryWithSubCategoriesDto dto) {
        List<SubCategoryVo> subCategoryVos = dto.getSubCategories().stream()
                .map(subDto -> SubCategoryVo.builder()
                        .subCategoryId(subDto.getSubCategoryId())
                        .subCategoryName(subDto.getSubCategoryName())
                        .color(subDto.getColor())
                        .build())
                .toList();
        
        return MainCategoryWithSubCategoriesVo.builder()
                .mainCategoryId(dto.getMainCategoryId())
                .mainCategoryName(dto.getMainCategoryName())
                .iconUrl(dto.getIconUrl())
                .alt(dto.getAlt())
                .subCategories(subCategoryVos)
                .build();
    }
} 