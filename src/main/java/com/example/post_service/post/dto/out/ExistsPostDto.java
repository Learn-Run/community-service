package com.example.post_service.post.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 존재 여부 응답")
public class ExistsPostDto {

    @Schema(description = "게시글 존재 여부", example = "true")
    private boolean existsPost;

    @Builder
    public ExistsPostDto(boolean existsPost) {
        this.existsPost = existsPost;
    }

    public static ExistsPostDto from(boolean existsPost) {
        return ExistsPostDto.builder()
                .existsPost(existsPost)
                .build();
    }
}
