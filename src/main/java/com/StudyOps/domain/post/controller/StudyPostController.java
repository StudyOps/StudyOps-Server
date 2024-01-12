package com.StudyOps.domain.post.controller;

import com.StudyOps.domain.post.dto.StudyPostReqDto;
import com.StudyOps.domain.post.dto.StudyPostResDto;
import com.StudyOps.domain.post.service.StudyPostService;
import com.StudyOps.global.common.ApiResponse;
import com.StudyOps.global.common.ApiResponseStatus;
import com.StudyOps.s3.service.S3Service;
import com.StudyOps.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.StudyOps.global.common.ApiResponseStatus.GET_POST_LIST_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudyPostController {
    private final StudyPostService studyPostService;
    private final S3Service s3Service;

    @PostMapping("/posts/{groupId}")
    public ResponseEntity<ApiResponse<Object>> createPost(@RequestPart(value = "files",required = false) List<MultipartFile> files, @RequestPart(value = "title") String title, @RequestPart(value = "contents") String contents, @PathVariable(value = "groupId") Long groupId) throws IOException {
        List<String> urls = new ArrayList<>();
        StudyPostReqDto studyPostReqDto = StudyPostReqDto.builder()
                .title(title)
                .contents(contents)
                .build();

        if (files != null) {
            for (MultipartFile file : files) {
                // 파일을 S3에 업로드하고 URL을 받아옴
                String url = s3Service.upload(file, "posts/files");
                // 받아온 URL을 리스트에 추가
                urls.add(url);
                studyPostService.createPostWithFiles(groupId, SecurityUtil.getCurrentMemberId(), studyPostReqDto, urls);
            }
        }
        else
            studyPostService.createPost(groupId, SecurityUtil.getCurrentMemberId(),studyPostReqDto);
        ApiResponse<Object> successResponse = new ApiResponse<>(ApiResponseStatus.CREATE_STUDY_POST_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
    @GetMapping("/posts/{groupId}")
    public ResponseEntity<ApiResponse<StudyPostResDto>> getPostList(@PageableDefault(size = 10) Pageable pageable, @PathVariable Long groupId){

        ApiResponse<StudyPostResDto> successResponse = new ApiResponse<>(GET_POST_LIST_SUCCESS,studyPostService.getPostList(pageable,groupId));
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
