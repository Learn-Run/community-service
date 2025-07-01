package com.example.post_service.category.presentation;

import com.example.post_service.category.application.MainCategoryService;
import com.example.post_service.category.dto.in.MainCategoryReqDto;
import com.example.post_service.category.dto.out.MainCategoryResDto;
import com.example.post_service.category.dto.out.SimpleSubCategoryResDto;
import com.example.post_service.category.vo.in.MainCategoryReqVo;
import com.example.post_service.category.vo.out.MainCategoryResVo;
import com.example.post_service.common.entity.BaseResponseEntity;
import com.example.post_service.common.response.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MainCategory", description = "메인 카테고리 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@SecurityRequirement(name = "JWT")
public class MainCategoryController {

    private final MainCategoryService mainCategoryService;

    @Operation(
        summary = "메인 카테고리 생성",
        description = """
            새로운 메인 카테고리를 생성합니다.
            
            ### 처리 과정
            1. 카테고리 데이터 검증
            2. 중복 카테고리명 확인
            3. MySQL에 카테고리 저장
            
            ### 주의사항
            - 카테고리명은 최대 100자까지 입력 가능
            - 아이콘 URL은 유효한 URL이어야 함
            - 대체 텍스트는 최대 200자까지 입력 가능
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "메인 카테고리 생성 성공"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "검증 실패",
                    value = """
                        {
                            "isSuccess": false,
                            "code": 2105,
                            "message": "유효하지 않은 카테고리 이름입니다.",
                            "result": null
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "중복된 카테고리명",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "중복 카테고리",
                    value = """
                        {
                            "isSuccess": false,
                            "code": 2104,
                            "message": "이미 존재하는 카테고리 이름입니다.",
                            "result": null
                        }
                        """
                )
            )
        )
    })
    @PostMapping("/main")
    public BaseResponseEntity<Void> createMainCategory(
            @Parameter(description = "메인 카테고리 생성 정보")
            @RequestBody MainCategoryReqVo mainCategoryReqVo
    ) {
        mainCategoryService.createMainCategory(MainCategoryReqDto.from(mainCategoryReqVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(
        summary = "메인 카테고리 전체 조회",
        description = """
            모든 메인 카테고리를 조회합니다.
            
            ### 응답 정보
            - 카테고리 ID
            - 카테고리 이름
            - 아이콘 URL
            - 대체 텍스트
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "메인 카테고리 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = BaseResponseEntity.class),
                examples = @ExampleObject(
                    name = "성공 응답",
                    value = """
                        {
                            "isSuccess": true,
                            "code": 200,
                            "message": "요청에 성공하였습니다.",
                            "result": [
                                {
                                    "id": 1,
                                    "name": "프로그래밍",
                                    "iconUrl": "https://example.com/icons/programming.png",
                                    "alt": "프로그래밍 아이콘"
                                },
                                {
                                    "id": 2,
                                    "name": "디자인",
                                    "iconUrl": "https://example.com/icons/design.png",
                                    "alt": "디자인 아이콘"
                                }
                            ]
                        }
                        """
                )
            )
        )
    })
    @GetMapping("/main")
    public BaseResponseEntity<List<MainCategoryResVo>> getAllMainCategory() {
        return new BaseResponseEntity<>(
                mainCategoryService.getAllMainCategory()
                        .stream()
                        .map(MainCategoryResDto::toVo)
                        .toList());
    }

    @Operation(
        summary = "메인 카테고리 단건 조회",
        description = """
            특정 메인 카테고리의 상세 정보를 조회합니다.
            
            ### 응답 정보
            - 카테고리 ID
            - 카테고리 이름
            - 아이콘 URL
            - 대체 텍스트
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "메인 카테고리 조회 성공"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "카테고리를 찾을 수 없음",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "카테고리 없음",
                    value = """
                        {
                            "isSuccess": false,
                            "code": 2100,
                            "message": "해당 카테고리를 찾을 수 없습니다.",
                            "result": null
                        }
                        """
                )
            )
        )
    })
    @GetMapping("/main/{id}")
    public BaseResponseEntity<MainCategoryResVo> getMainCategory(
            @Parameter(description = "메인 카테고리 ID", example = "1")
            @PathVariable Long id
    ) {
        return new BaseResponseEntity<>(
                MainCategoryResVo.of(mainCategoryService.getMainCategoryById(id)));
    }

    @Operation(
        summary = "메인 카테고리 수정",
        description = """
            기존 메인 카테고리를 수정합니다.
            
            ### 처리 과정
            1. 카테고리 존재 여부 확인
            2. 카테고리 데이터 검증
            3. 카테고리 정보 업데이트
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "메인 카테고리 수정 성공"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "카테고리를 찾을 수 없음"
        )
    })
    @PutMapping("/main/{id}")
    public BaseResponseEntity<Void> updateMainCategory(
            @Parameter(description = "메인 카테고리 ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "메인 카테고리 수정 정보")
            @RequestBody MainCategoryReqVo mainCategoryReqVo
    ) {
        mainCategoryService.updateMainCategory(id, MainCategoryReqDto.from(mainCategoryReqVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "메인 카테고리 삭제")
    @DeleteMapping("/main/{id}")
    public BaseResponseEntity<Void> deleteMainCategory(@PathVariable Long id) {
        mainCategoryService.deleteMainCategory(id);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "메인 카테고리에 속한 서브 카테고리 전체 조회")
    @GetMapping("/main/{id}/sub-categories")
    public BaseResponseEntity<List<SimpleSubCategoryResDto>> getSubCategoriesByMainCategoryId (@PathVariable Long id) {
        return new BaseResponseEntity<>(mainCategoryService.getSubCategoriesByMainCategoryId(id));
    }
}
