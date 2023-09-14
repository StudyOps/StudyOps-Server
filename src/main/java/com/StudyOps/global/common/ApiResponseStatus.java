package com.StudyOps.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseStatus {
    //이 부분에 응답상태 기록
    STUDY_GROUP_CREATE_SUCCESS(true,201,"스터디 그룹 생성 성공!");
    private final boolean isSuccess;
    private final int status;
    private final String message;
}
