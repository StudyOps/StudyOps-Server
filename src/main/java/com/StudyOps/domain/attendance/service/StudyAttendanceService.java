package com.StudyOps.domain.attendance.service;

import com.StudyOps.domain.attendance.entity.StudyAttendance;
import com.StudyOps.domain.attendance.repository.StudyAttendanceRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyAttendanceService {
    private final StudyAttendanceRepository studyAttendanceRepository;

    public void deleteStudyMember(StudyMember studyMember){
        List<StudyAttendance> attendances = studyAttendanceRepository.findAllByStudyMember(studyMember);
        attendances.stream()
                .forEach(attendance -> studyAttendanceRepository.delete(attendance));
    }
}
