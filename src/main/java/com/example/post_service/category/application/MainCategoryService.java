package com.example.post_service.category.application;

import com.example.post_service.category.dto.in.MainCategoryReqDto;
import com.example.post_service.category.dto.out.MainCategoryResDto;
import com.example.post_service.category.dto.out.MainCategoryWithSubCategoriesDto;
import com.example.post_service.category.dto.out.SimpleSubCategoryResDto;

import java.util.List;

public interface MainCategoryService {

    void createMainCategory(MainCategoryReqDto mainCategoryReqDto);

    List<MainCategoryResDto> getAllMainCategory();

    MainCategoryResDto getMainCategoryById(Long id);

    void updateMainCategory(Long id, MainCategoryReqDto mainCategoryReqDto);

    void deleteMainCategory(Long mainCategoryId);

    List<SimpleSubCategoryResDto> getSubCategoriesByMainCategoryId(Long mainCategoryId);
    
    List<MainCategoryWithSubCategoriesDto> getAllMainCategoriesWithSubCategories();
}
