package com.StudyOps.domain.attendance.service;

import com.StudyOps.domain.attendance.entity.StudyAttendanceVote;
import com.StudyOps.domain.attendance.repository.StudyAttendanceVoteRepository;
import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.user.entity.User;
import com.StudyOps.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyAttendanceVoteService {
    private final StudyAttendanceVoteRepository studyAttendanceVoteRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;
    public void deleteStudyMember(StudyMember studyMember){
        List<StudyAttendanceVote> attendanceVotes = studyAttendanceVoteRepository.findAllByStudyMember(studyMember);
        attendanceVotes.stream()
                .forEach(attendanceVote -> studyAttendanceVoteRepository.delete(attendanceVote));
    }
    public void absentStudyDate(Long groupId, Long userId, String date) {
        StudyGroup studyGroup =  studyGroupRepository.findById(groupId).get();
        User user = userRepository.findById(userId).get();
        StudyMember studyMember = studyMemberRepository.findByStudyGroupAndUser(studyGroup, user).get();

        LocalDate absentDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        StudyAttendanceVote studyAttendanceVote = StudyAttendanceVote.builder()
                .attendance(false)
                .studyMember(studyMember)
                .date(absentDate)
                .build();
        studyAttendanceVoteRepository.save(studyAttendanceVote);
    }
}
