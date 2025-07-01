package com.example.post_service.post.vo.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 정보 응답")
public class GetPostInfoResVo {

    @Schema(description = "사용자 UUID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String memberUuid;
    
    @Schema(description = "게시글 UUID", example = "550e8400-e29b-41d4-a716-446655440001")
    private String postUuid;
    
    @Schema(description = "게시글 제목", example = "Spring Boot 마이크로서비스 아키텍처에 대한 질문")
    private String postTitle;

    @Builder
    public GetPostInfoResVo(String memberUuid, String postUuid, String postTitle) {
        this.memberUuid = memberUuid;
        this.postUuid = postUuid;
        this.postTitle = postTitle;
    }
}
