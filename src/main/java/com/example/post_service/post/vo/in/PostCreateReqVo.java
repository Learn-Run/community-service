package com.example.post_service.post.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "게시글 생성 요청")
public class PostCreateReqVo {
    
    @Schema(description = "메인 카테고리 ID", example = "1", required = true)
    private Long mainCategoryId;
    
    @Schema(description = "서브 카테고리 ID", example = "10", required = true)
    private Long subCategoryId;
    
    @Schema(description = "게시글 제목", example = "Spring Boot 마이크로서비스 아키텍처에 대한 질문", 
            maxLength = 200, required = true)
    private String title;
    
    @Schema(description = "게시글 내용", example = "MSA 환경에서 Spring Boot를 사용한 서비스 간 통신 방법에 대해 궁금합니다.", 
            maxLength = 10000, required = true)
    private String contents;

    @Builder
    public PostCreateReqVo(
            Long mainCategoryId,
            Long subCategoryId,
            String title,
            String contents
    ) {
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
        this.title = title;
        this.contents = contents;
    }
}
