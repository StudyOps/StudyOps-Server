package com.StudyOps.domain.attendance.service;

import com.StudyOps.domain.attendance.dto.StudyAttendanceAndAbsenceDto;
import com.StudyOps.domain.attendance.dto.StudyScheduleAndAttendanceResDto;
import com.StudyOps.domain.attendance.entity.StudyAttendance;
import com.StudyOps.domain.attendance.entity.StudyAttendanceVote;
import com.StudyOps.domain.attendance.repository.StudyAttendanceRepository;
import com.StudyOps.domain.attendance.repository.StudyAttendanceVoteRepository;
import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.member.entity.StudyMember;
import com.StudyOps.domain.member.repository.StudyMemberRepository;
import com.StudyOps.domain.penalty.entity.StudyLatePenalty;
import com.StudyOps.domain.penalty.repository.StudyPenaltyRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyAttendanceService {
    private final StudyAttendanceRepository studyAttendanceRepository;
    private final StudyAttendanceVoteRepository studyAttendanceVoteRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final StudyPenaltyRepository studyPenaltyRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyScheduleRepository studyScheduleRepository;

    public void deleteStudyMember(StudyMember studyMember) {
        List<StudyAttendance> attendances = studyAttendanceRepository.findAllByStudyMember(studyMember);
        attendances.stream()
                .forEach(attendance -> studyAttendanceRepository.delete(attendance));
    }

    public void attendStudyDate(Long groupId, Long userId) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        User user = userRepository.findById(userId).get();
        StudyMember studyMember = studyMemberRepository.findByStudyGroupAndUser(studyGroup, user).get();

        //지각 계산 로직
        LocalDateTime attendTime = LocalDateTime.now();
        //현재 날짜의 요일을 구하고 ScheduleRepository에서 해당 스터디와 정보로 Schedule을 찾음
        String currentDayWeek = attendTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        StudySchedule studySchedule = studyScheduleRepository.findByStudyGroupAndDayWeek(studyGroup, currentDayWeek).get();

        //출석시간과 스터디시작 시간 차이를 구함
        LocalTime startTime = studySchedule.getStartTime();
        LocalDateTime start = LocalDateTime.now().with(startTime); //
        if (attendTime.isBefore(start)) {
            attendTime = attendTime.plusDays(1); // 일자 변경 처리
        }

        Duration duration = Duration.between(startTime, attendTime);
        int timeDifference = (int) duration.toMinutes();

        if (timeDifference > studyGroup.getAllowedTime()) {
            StudyLatePenalty studyLatePenalty = StudyLatePenalty.builder()
                    .studyMember(studyMember)
                    .studyGroup(studyGroup)
                    .fine(studyGroup.getLateCost())
                    .isSettled(false)
                    .lateTime(timeDifference)
                    .date(LocalDate.now())
                    .build();
            studyPenaltyRepository.save(studyLatePenalty);
        }
        StudyAttendance studyAttendance = StudyAttendance.builder()
                .studyMember(studyMember)
                .time(LocalDateTime.now())
                .date(LocalDate.now())
                .lateTime(timeDifference)
                .isLate(timeDifference > studyGroup.getAllowedTime())
                .build();
        studyAttendanceRepository.save(studyAttendance);
    }

    public StudyScheduleAndAttendanceResDto getStudyScheduleAndAttendance(Long groupId, Long userId) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        User user = userRepository.findById(userId).get();
        StudyMember studyMember = studyMemberRepository.findByStudyGroupAndUser(studyGroup, user).get();

        LocalDateTime currentTime = LocalDateTime.now();
        String currentDayWeek = currentTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        Optional<StudySchedule> studySchedule = studyScheduleRepository.findByStudyGroupAndDayWeek(studyGroup, currentDayWeek);
        if (studySchedule.isEmpty())
            return StudyScheduleAndAttendanceResDto.builder()
                    .isStudyDay(false)
                    .build();
        Optional<StudyAttendance> studyAttendance = studyAttendanceRepository.findByStudyMemberAndDate(studyMember, LocalDate.now());
        if (studyAttendance.isEmpty())
            return StudyScheduleAndAttendanceResDto.builder()
                    .isStudyDay(true)
                    .startTime(studySchedule.get().getStartTime())
                    .finishTime(studySchedule.get().getFinishTime())
                    .isAttendant(false)
                    .build();
        return StudyScheduleAndAttendanceResDto.builder()
                .isStudyDay(true)
                .isAttendant(true)
                .isLate(studyAttendance.get().getIsLate())
                .startTime(studySchedule.get().getStartTime())
                .lateTime(studyAttendance.get().getLateTime())
                .attendanceTime(studyAttendance.get().getTime())
                .build();
    }

    public StudyAttendanceAndAbsenceDto getStudyAttendanceByDate(Long groupId, Long userId, String date) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        List<StudyMember> members = studyMemberRepository.findAllByStudyGroup(studyGroup);

        LocalDate today = LocalDate.now();
        LocalDate studyDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);

        List<String> absentMemberList = new ArrayList<>();
        List<String> attendMemberList = new ArrayList<>();

        if (today.isAfter(studyDate)) {
            for (int i = 0; i < members.size(); i++) {
                Optional<StudyAttendance> studyAttendance = studyAttendanceRepository.findByStudyMemberAndDate(members.get(i), studyDate);
                if (studyAttendance.isEmpty())
                    absentMemberList.add(members.get(i).getUser().getNickname());
                else {
                    attendMemberList.add(members.get(i).getUser().getNickname());
                }
            }
        } else {
            for (int i = 0; i < members.size(); i++) {
                Optional<StudyAttendanceVote> studyAttendanceVote = studyAttendanceVoteRepository.findByStudyMemberAndDate(members.get(i), studyDate);
                if (studyAttendanceVote.isEmpty())
                    attendMemberList.add(members.get(i).getUser().getNickname());
                else if (studyAttendanceVote.get().getAttendance())
                    attendMemberList.add(members.get(i).getUser().getNickname());
                else
                    absentMemberList.add(members.get(i).getUser().getNickname());
            }

        }
        return StudyAttendanceAndAbsenceDto.builder()
                .attendMemberList(attendMemberList)
                .absenceMemberList(absentMemberList)
                .isAttended(attendMemberList.contains(userRepository.findById(userId).get().getNickname()))
                .build();
    }
}
