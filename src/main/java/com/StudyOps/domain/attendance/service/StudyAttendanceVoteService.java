package com.StudyOps.domain.attendance.service;

import com.StudyOps.domain.attendance.entity.StudyAttendanceVote;
import com.StudyOps.domain.attendance.repository.StudyAttendanceVoteRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyAttendanceVoteService {
    private final StudyAttendanceVoteRepository studyAttendanceVoteRepository;
    public void deleteStudyMember(StudyMember studyMember){
        List<StudyAttendanceVote> attendanceVotes = studyAttendanceVoteRepository.findAllByStudyMember(studyMember);
        attendanceVotes.stream()
                .forEach(attendanceVote -> studyAttendanceVoteRepository.delete(attendanceVote));
    }
}
