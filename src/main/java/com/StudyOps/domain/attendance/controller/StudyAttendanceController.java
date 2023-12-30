package com.StudyOps.domain.attendance.controller;

import com.StudyOps.domain.attendance.dto.StudyAttendanceAndAbsenceDto;
import com.StudyOps.domain.attendance.dto.StudyScheduleAndAttendanceResDto;
import com.StudyOps.domain.attendance.service.StudyAttendanceService;
import com.StudyOps.global.common.ApiResponse;
import com.StudyOps.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.StudyOps.global.common.ApiResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class StudyAttendanceController {
    private final StudyAttendanceService studyAttendanceService;

    @PostMapping("/schedules/attendances/{groupId}")
    public ResponseEntity<ApiResponse<Object>> attendStudyDate(@PathVariable(value = "groupId") Long groupId) {

        studyAttendanceService.attendStudyDate(groupId, SecurityUtil.getCurrentMemberId());

        ApiResponse<Object> successResponse = new ApiResponse<>(STUDY_DATE_ATTEND_SUCCESS);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @GetMapping("/schedules/today/{groupId}")
    public ResponseEntity<ApiResponse<StudyScheduleAndAttendanceResDto>> getStudyGroupScheduleAndAttendance(@PathVariable(value = "groupId") Long groupId) {

        ApiResponse<StudyScheduleAndAttendanceResDto> successResponse = new ApiResponse<>(STUDY_SCHEDULE_ATTENDANCE_GET_SUCCESS, studyAttendanceService.getStudyScheduleAndAttendance(groupId, SecurityUtil.getCurrentMemberId()));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/schedules/attendances/{groupId}")
    public ResponseEntity<ApiResponse<StudyAttendanceAndAbsenceDto>> getStudyAttendanceByDate(@PathVariable(value = "groupId") Long groupId, @RequestParam String date) {


        ApiResponse<StudyAttendanceAndAbsenceDto> successResponse = new ApiResponse<>(STUDY_ATTENDANCE_GET_BY_DATE_SUCCESS, studyAttendanceService.getStudyAttendanceByDate(groupId, SecurityUtil.getCurrentMemberId(), date));

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
