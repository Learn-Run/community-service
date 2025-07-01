package com.example.post_service.post.presentation;

import com.example.post_service.common.entity.BaseResponseEntity;
import com.example.post_service.common.response.BaseResponseStatus;
import com.example.post_service.post.application.PostService;
import com.example.post_service.post.dto.in.PostCreateReqDto;
import com.example.post_service.post.dto.in.PostUpdateReqDto;
import com.example.post_service.post.dto.out.ExistsPostDto;
import com.example.post_service.post.vo.in.PostCreateReqVo;
import com.example.post_service.post.vo.in.PostUpdateReqVo;
import com.example.post_service.post.vo.out.GetPostInfoResVo;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name = "Post", description = "게시글 관리 API")
@SecurityRequirement(name = "JWT")
@Slf4j
public class PostController {

    private final PostService postService;

    @Operation(
        summary = "게시글 생성",
        description = """
            새로운 게시글을 생성합니다.
            
            ### 처리 과정
            1. 게시글 데이터 검증
            2. MongoDB에 게시글 저장
            3. Kafka 이벤트 발행 (post-create-send-read 토픽)
            
            ### 주의사항
            - 제목은 최대 200자까지 입력 가능
            - 내용은 최대 10,000자까지 입력 가능
            - 카테고리 ID는 유효한 값이어야 함
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "게시글 생성 성공",
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
                            "result": null
                        }
                        """
                )
            )
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
                            "code": 2004,
                            "message": "게시글 내용이 너무 깁니다.",
                            "result": null
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증 실패",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "인증 실패",
                    value = """
                        {
                            "isSuccess": false,
                            "code": 1001,
                            "message": "로그인을 먼저 진행해주세요",
                            "result": null
                        }
                        """
                )
            )
        )
    })
    @PostMapping("/create")
    public BaseResponseEntity<Void> createPost(
            @Parameter(description = "사용자 UUID (Gateway에서 설정)", example = "550e8400-e29b-41d4-a716-446655440000")
            @RequestHeader("X-Member-UUID") String memberUuid,
            @Parameter(description = "게시글 생성 정보")
            @RequestBody PostCreateReqVo postCreateReqVo
    ) {
        postService.createPost(PostCreateReqDto.of(memberUuid, postCreateReqVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(
        summary = "게시글 수정",
        description = """
            기존 게시글을 수정합니다.
            
            ### 권한 검증
            - 게시글 작성자만 수정 가능
            - X-Member-UUID와 게시글의 memberUuid 일치 여부 확인
            
            ### 처리 과정
            1. 게시글 존재 여부 확인
            2. 수정 권한 검증
            3. 게시글 정보 업데이트
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "게시글 수정 성공"
        ),
        @ApiResponse(
            responseCode = "403",
            description = "수정 권한 없음",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "권한 없음",
                    value = """
                        {
                            "isSuccess": false,
                            "code": 2008,
                            "message": "게시글에 대한 권한이 없습니다.",
                            "result": null
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "게시글 없음",
                    value = """
                        {
                            "isSuccess": false,
                            "code": 2000,
                            "message": "해당 게시글을 찾을 수 없습니다.",
                            "result": null
                        }
                        """
                )
            )
        )
    })
    @PatchMapping("/{postUuid}")
    public BaseResponseEntity<Void> updatePost(
            @Parameter(description = "사용자 UUID (Gateway에서 설정)", example = "550e8400-e29b-41d4-a716-446655440000")
            @RequestHeader("X-Member-UUID") String memberUuid,
            @Parameter(description = "게시글 UUID", example = "550e8400-e29b-41d4-a716-446655440001")
            @PathVariable String postUuid,
            @Parameter(description = "게시글 수정 정보")
            @RequestBody PostUpdateReqVo postUpdateReqVo
    ) {
        postService.updatePost(memberUuid, postUuid, PostUpdateReqDto.from(postUpdateReqVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(
        summary = "게시글 정보 조회",
        description = """
            게시글의 상세 정보를 조회합니다.
            
            ### 응답 정보
            - 게시글 UUID
            - 작성자 UUID
            - 게시글 제목
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "게시글 정보 조회 성공",
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
                            "result": {
                                "memberUuid": "550e8400-e29b-41d4-a716-446655440000",
                                "postUuid": "550e8400-e29b-41d4-a716-446655440001",
                                "postTitle": "Spring Boot 마이크로서비스 아키텍처에 대한 질문"
                            }
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음"
        )
    })
    @GetMapping("/{postUuid}")
    public BaseResponseEntity<GetPostInfoResVo> getPostInfo(
            @Parameter(description = "게시글 UUID", example = "550e8400-e29b-41d4-a716-446655440001")
            @PathVariable String postUuid
    ) {
        return new BaseResponseEntity<>(postService.getPostInfo(postUuid).toVo());
    }

    @Operation(
        summary = "게시글 존재 여부 확인",
        description = """
            게시글 UUID를 통해 해당 게시글이 존재하는지 확인합니다.
            
            ### 사용 사례
            - 게시글 링크 유효성 검증
            - 게시글 삭제 여부 확인
            - 빠른 존재 여부 체크 (전체 데이터 조회 없이)
            
            ### 응답
            - exists: true (존재함)
            - exists: false (존재하지 않음)
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "존재 여부 확인 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = BaseResponseEntity.class),
                examples = {
                    @ExampleObject(
                        name = "존재함",
                        value = """
                            {
                                "isSuccess": true,
                                "code": 200,
                                "message": "요청에 성공하였습니다.",
                                "result": {
                                    "existsPost": true
                                }
                            }
                            """
                    ),
                    @ExampleObject(
                        name = "존재하지 않음",
                        value = """
                            {
                                "isSuccess": true,
                                "code": 200,
                                "message": "요청에 성공하였습니다.",
                                "result": {
                                    "existsPost": false
                                }
                            }
                            """
                    )
                }
            )
        )
    })
    @GetMapping("/exist/{postUuid}")
    public BaseResponseEntity<ExistsPostDto> existsPost(
            @Parameter(description = "게시글 UUID", example = "550e8400-e29b-41d4-a716-446655440001")
            @PathVariable String postUuid
    ){
        return new BaseResponseEntity<>(postService.existsPost(postUuid));
    }
}
