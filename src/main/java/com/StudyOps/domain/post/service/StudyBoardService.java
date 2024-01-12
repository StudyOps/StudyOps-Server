package com.StudyOps.domain.post.service;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.post.dto.StudyPostReqDto;
import com.StudyOps.domain.post.entity.StudyPost;
import com.StudyOps.domain.post.entity.StudyPostFile;
import com.StudyOps.domain.post.repository.StudyPostFileRepository;
import com.StudyOps.domain.post.repository.StudyPostRepository;
import com.StudyOps.domain.user.entity.EndUser;
import com.StudyOps.domain.user.repository.EndUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class StudyBoardService {
    private final StudyMemberRepository studyMemberRepository;
    private final EndUserRepository endUserRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyPostRepository studyPostRepository;
    private final StudyPostFileRepository studyPostFileRepository;

    public Long createPost(Long groupId, Long userId, StudyPostReqDto studyPostReqDto) {
        EndUser user = endUserRepository.findById(userId).get();
        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        StudyMember studyMember = studyMemberRepository.findByStudyGroupAndEndUser(studyGroup,user).get();

        return  studyPostRepository.save(StudyPost.builder()
                .studyMember(studyMember)
                .studyGroup(studyGroup)
                .title(studyPostReqDto.getTitle())
                .contents(studyPostReqDto.getContents())
                .writer(user.getNickname())
                .date(LocalDate.now())
                .build()).getId();
    }
    public void createPostWithFiles(Long groupId, Long userId, StudyPostReqDto studyPostReqDto, List<String> urls) {
        StudyPost studyPost = studyPostRepository.findById(createPost(groupId, userId, studyPostReqDto)).get();
        for(String url : urls){
            studyPostFileRepository.save(StudyPostFile.builder()
                    .studyPost(studyPost)
                    .url(url)
                    .build());
        }
    }
}
