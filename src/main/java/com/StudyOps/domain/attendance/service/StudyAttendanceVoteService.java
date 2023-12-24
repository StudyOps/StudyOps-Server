package com.StudyOps.domain.attendance.service;

import com.StudyOps.domain.attendance.entity.StudyAttendanceVote;
import com.StudyOps.domain.attendance.repository.StudyAttendanceVoteRepository;
import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.user.entity.EndUser;
import com.StudyOps.domain.user.repository.EndUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyAttendanceVoteService {
    private final StudyAttendanceVoteRepository studyAttendanceVoteRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final EndUserRepository endUserRepository;
    private final StudyMemberRepository studyMemberRepository;
    public void deleteStudyMember(StudyMember studyMember){
        List<StudyAttendanceVote> attendanceVotes = studyAttendanceVoteRepository.findAllByStudyMember(studyMember);
        attendanceVotes.stream()
                .forEach(attendanceVote -> studyAttendanceVoteRepository.delete(attendanceVote));
    }
    public void absentOrAttendStudyDate(Long groupId, Long userId, String date,Boolean attendance) {
        StudyGroup studyGroup =  studyGroupRepository.findById(groupId).get();
        EndUser endUser = endUserRepository.findById(userId).get();
        StudyMember studyMember = studyMemberRepository.findByStudyGroupAndEndUser(studyGroup, endUser).get();
        LocalDate absentDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);

        Optional<StudyAttendanceVote> studyAttendanceVote =studyAttendanceVoteRepository.findByStudyMemberAndDate(studyMember,absentDate);

        if(studyAttendanceVote.isEmpty()){
            StudyAttendanceVote vote = StudyAttendanceVote.builder()
                    .attendance(attendance)
                    .studyMember(studyMember)
                    .date(absentDate)
                    .build();
            studyAttendanceVoteRepository.save(vote);
        }
        else{
            if(attendance)
                studyAttendanceVote.get().attend();
            else
                studyAttendanceVote.get().absent();
        }
    }
}
