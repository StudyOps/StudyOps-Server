package com.StudyOps.domain.attendance.service;

import com.StudyOps.domain.attendance.entity.StudyAttendance;
import com.StudyOps.domain.attendance.repository.StudyAttendanceRepository;
import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.schedule.entity.StudySchedule;
import com.StudyOps.domain.schedule.repository.StudyScheduleRepository;
import com.StudyOps.domain.user.entity.User;
import com.StudyOps.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyAttendanceService {
    private final StudyAttendanceRepository studyAttendanceRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyScheduleRepository studyScheduleRepository;

    public void deleteStudyMember(StudyMember studyMember){
        List<StudyAttendance> attendances = studyAttendanceRepository.findAllByStudyMember(studyMember);
        attendances.stream()
                .forEach(attendance -> studyAttendanceRepository.delete(attendance));
    }

    public void attendStudyDate(Long groupId, Long userId) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        User user = userRepository.findById(userId).get();
        StudyMember studyMember = studyMemberRepository.findByStudyGroupAndUser(studyGroup, user).get();

        //지각 계산 로직
        LocalDateTime currentTime = LocalDateTime.now();
        //현재 날짜의 요일을 구하고 ScheduleRepository에서 해당 스터디와 정보로 Schedule을 찾음
        String curreDayWeek = currentTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        StudySchedule studySchedule = studyScheduleRepository.findByStudyGroupAndDayWeek(studyGroup,curreDayWeek).get();

        //출석시간과 스터디시작 시간 차이를 구함
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime attendTime = LocalTime.now();
        LocalTime startTime = LocalTime.parse(studySchedule.getStartTime(),formatter);
        Duration duration = Duration.between(startTime,attendTime);
        int timeDifference = (int)duration.toMinutes();

        if(timeDifference <= 0)
            timeDifference = 0;
        StudyAttendance studyAttendance = StudyAttendance.builder()
                .studyMember(studyMember)
                .date(LocalDate.now())
                .lateTime(timeDifference)
                .isLate(timeDifference>0)
                .build();
        studyAttendanceRepository.save(studyAttendance);
    }
}
