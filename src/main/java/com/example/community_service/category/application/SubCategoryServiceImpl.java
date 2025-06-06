package com.example.community_service.category.application;

import com.example.community_service.category.dto.in.SubCategoryReqDto;
import com.example.community_service.category.dto.in.SubCategoryReqDto;
import com.example.community_service.category.dto.out.SubCategoryResDto;
import com.example.community_service.category.dto.out.SubCategoryResDto;
import com.example.community_service.category.entity.SubCategory;
import com.example.community_service.category.entity.SubCategory;
import com.example.community_service.category.infrastructure.SubCategoryRepository;
import com.example.community_service.common.entity.BaseResponseStatus;
import com.example.community_service.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    @Transactional
    @Override
    public void createSubCategory(SubCategoryReqDto subCategoryReqDto) {
        subCategoryRepository.save(subCategoryReqDto.toEntity());
    }

    @Override
    public List<SubCategoryResDto> getAllSubCategory() {
        return subCategoryRepository.findAll()
                .stream()
                .map(SubCategoryResDto::from)
                .toList();
    }

    @Transactional
    @Override
    public void updateSubCategory(Long id, SubCategoryReqDto dto) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.CATEGORY_NOT_FOUND));

        subCategory.updateName(dto.getSubCategoryName());
    }

    @Transactional
    @Override
    public void deleteSubCategory(Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.CATEGORY_NOT_FOUND)
        );

        subCategoryRepository.delete(subCategory);
    }
}
