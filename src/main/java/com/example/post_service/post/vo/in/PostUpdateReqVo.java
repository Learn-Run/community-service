package com.example.post_service.post.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "게시글 수정 요청")
public class PostUpdateReqVo {
    
    @Schema(description = "게시글 제목", example = "수정된 Spring Boot 마이크로서비스 질문", 
            maxLength = 200, required = true)
    private String title;
    
    @Schema(description = "게시글 내용", example = "수정된 내용: MSA 환경에서 Spring Boot를 사용한 서비스 간 통신 방법에 대해 궁금합니다.", 
            maxLength = 10000, required = true)
    private String contents;
    
    @Schema(description = "메인 카테고리 ID", example = "1", required = true)
    private Long mainCategoryId;
    
    @Schema(description = "서브 카테고리 ID", example = "10", required = true)
    private Long subCategoryId;
}