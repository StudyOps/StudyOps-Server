package com.StudyOps.domain.post.service;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.post.dto.StudyPostClickDto;
import com.StudyOps.domain.post.dto.StudyPostReqDto;
import com.StudyOps.domain.post.dto.StudyPostDto;
import com.StudyOps.domain.post.dto.StudyPostResDto;
import com.StudyOps.domain.post.entity.StudyPost;
import com.StudyOps.domain.post.entity.StudyPostFile;
import com.StudyOps.domain.post.repository.StudyPostFileRepository;
import com.StudyOps.domain.post.repository.StudyPostRepository;
import com.StudyOps.domain.user.entity.EndUser;
import com.StudyOps.domain.user.repository.EndUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class StudyPostService {
    private final StudyMemberRepository studyMemberRepository;
    private final EndUserRepository endUserRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyPostRepository studyPostRepository;
    private final StudyPostFileRepository studyPostFileRepository;

    public StudyPost createPost(Long groupId, Long userId, StudyPostReqDto studyPostReqDto) {
        EndUser user = endUserRepository.findById(userId).get();
        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        StudyMember studyMember = studyMemberRepository.findByStudyGroupAndEndUser(studyGroup,user).get();

        return  studyPostRepository.save(StudyPost.builder()
                .studyMember(studyMember)
                .studyGroup(studyGroup)
                .title(studyPostReqDto.getTitle())
                .contents(studyPostReqDto.getContents())
                .date(LocalDate.now())
                .build());
    }
    public void createPostWithFiles(Long groupId, Long userId, StudyPostReqDto studyPostReqDto, List<String> urls) {
        StudyPost studyPost = createPost(groupId, userId, studyPostReqDto);
        for(String url : urls){
            studyPostFileRepository.save(StudyPostFile.builder()
                    .studyPost(studyPost)
                    .url(url)
                    .build());
        }
    }
    public StudyPostResDto getPostList(Pageable pageable, Long groupId) {
        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        Page<StudyPost> posts = studyPostRepository.findAllByStudyGroupOrderByIdDesc(pageable, studyGroup);

        return  StudyPostResDto.builder()
                .studyPostDtoList(posts.getContent().stream()
                                .map(this::convertToDto)
                                .collect(Collectors.toList()))
                .total(studyPostRepository.countStudyPostByStudyGroup(studyGroup))
                .build();

    }
    private StudyPostDto convertToDto(StudyPost studyPost) {
        return StudyPostDto.builder()
                .postId(studyPost.getId())
                .title(studyPost.getTitle())
                .writer(studyPost.getStudyMember().getEndUser().getNickname())
                .email(studyPost.getStudyMember().getEndUser().getEmail())
                .date(studyPost.getDate())
                .build();
    }

    public StudyPostClickDto getClickPost(Long postId) {
        List<String> urls = new ArrayList<>();

        StudyPost studyPost = studyPostRepository.findById(postId).get();
        List<StudyPostFile> studyPostFiles = studyPostFileRepository.findAllByStudyPost(studyPost);
        for(StudyPostFile studyPostFile :studyPostFiles){
            urls.add(studyPostFile.getUrl());
        }
        return StudyPostClickDto.builder()
                .urls(urls)
                .writer(studyPost.getStudyMember().getEndUser().getNickname())
                .email(studyPost.getStudyMember().getEndUser().getEmail())
                .contents(studyPost.getContents())
                .title(studyPost.getTitle())
                .date(studyPost.getDate())
                .build();
    }

    public void deletePost(Long postId) {
        StudyPost studyPost = studyPostRepository.findById(postId).get();
        List<StudyPostFile> studyPostFiles = studyPostFileRepository.findAllByStudyPost(studyPost);
        for(StudyPostFile studyPostFile :studyPostFiles){
            studyPostFileRepository.delete(studyPostFile);
        }
        studyPostRepository.delete(studyPost);
    }
}
