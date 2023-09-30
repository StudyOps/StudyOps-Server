package com.StudyOps.domain.schedule.service;

import com.StudyOps.domain.group.entity.StudyGroup;
import com.StudyOps.domain.group.repository.StudyGroupRepository;
import com.StudyOps.domain.schedule.dto.StudyScheduleDto;
import com.StudyOps.domain.schedule.dto.StudyScheduleListDto;
import com.StudyOps.domain.schedule.entity.StudySchedule;
import com.StudyOps.domain.schedule.repository.StudyScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyScheduleService {
    private final StudyScheduleRepository studyScheduleRepository;
    private final StudyGroupRepository studyGroupRepository;

    public void createStudySchedule(StudyGroup studyGroup, List<StudyScheduleDto> studySchedules){
        //스트림과 맵을 활용하여 StudyScheduleDto 리스트에 있는 각 Schedule을 디비에 저장한다.
        studySchedules.stream()
                .map(schedule -> StudySchedule.builder()
                        .studyGroup(studyGroup)
                        .dayWeek(schedule.getDayWeek())
                        .startTime(schedule.getStartTime())
                        .finishTime(schedule.getFinishTime())
                        .build())
                .forEach(studyScheduleRepository::save);
    }

    public StudyScheduleListDto getStudySchedule(Long groupId) {

        List<StudyScheduleDto> studyScheduleDtos = new ArrayList<>();
        StudyGroup studyGroup = studyGroupRepository.findById(groupId).get();
        List<StudySchedule> schedules = studyScheduleRepository.findAllByStudyGroup(studyGroup);

        for(int i=0; i<schedules.size(); i++){
            StudyScheduleDto studyScheduleDto = StudyScheduleDto.builder()
                    .dayWeek(schedules.get(i).getDayWeek())
                    .startTime(schedules.get(i).getStartTime())
                    .finishTime(schedules.get(i).getFinishTime())
                    .build();
            studyScheduleDtos.add(studyScheduleDto);
        }
        return StudyScheduleListDto.builder()
                .startDate(studyGroup.getStartDate())
                .schedules(studyScheduleDtos)
                .build();
    }
}
