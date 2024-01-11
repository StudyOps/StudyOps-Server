package com.StudyOps.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseStatus {
    //이 부분에 응답상태 기록
    STUDY_GROUP_CREATE_SUCCESS(true,201,"새로운 스터디 그룹 생성되었습니다."),
    STUDY_GROUP_QUIT_SUCCESS(true,200,"현재 스터디 그룹을 탈퇴했습니다."),
    ALL_STUDY_GROUPS_GET_SUCCESS(true, 200, "참여중인 스터디 목록 조회에 성공하였습니다."),
    INVITED_MEMBER_CREATE_SUCCESS(true,201,"스터디 초대에 성공하였습니다."),
    INVITED_MEMBER_ACCEPT_SUCCESS(true, 201, "스터디 초대를 수락하였습니다."),
    INVITED_MEMBER_REJECT_SUCCESS(true, 200, "스터디 초대를 거절하였습니다."),
    ALL_INVITED_STUDY_GROUPS_GET_SUCCESS(true,200,"초대받은 스터디 목록 조회에 성공하였습니다."),
    STUDY_GROUP_INFO_GET_SUCCESS(true, 200, "스터디 정보 조회에 성공하였습니다."),
    STUDY_DATE_ATTEND_SUCCESS(true,201, "스터디 출석에 성공하였습니다."),
    STUDY_DATE_ABSENT_SUCCESS(true, 200, "해당 날짜 참석여부 변경에 성공하였습니다."),
    STUDY_SCHEDULE_ATTENDANCE_GET_SUCCESS(true, 200, "스터디 해당 날짜 여부 조회 및 출석 조회에 성공하였습니다."),
    STUDY_SCHEDULE_GET_SUCCESS(true,200,"스터디 일정 정보 조회에 성공하였습니다."),
    STUDY_ATTENDANCE_GET_BY_DATE_SUCCESS(true,200,"해당 날짜 스터디 참석 인원 정보 조회에 성공하였습니다."),
    STUDY_GROUP_RULE_CHANGE_SUCCESS(true,200,"스터디 그룹 규칙 수정에 성공하였습니다."),
    STUDY_GROUP_INTRO_CHANGE_SUCCESS(true,200,"스터디 그룹 소개 수정에 성공하였습니다."),
    STUDY_GROUP_ACCOUNT_CHANGE_SUCCESS(true,200,"스터디 그룹 벌금 계좌 수정에 성공하였습니다."),
    STUDY_PENALTY_INFO_GET_SUCCESS(true,200, "스터디 그룹 벌금 정보 조회에 성공하였습니다."),
    NOT_SETTLED_DAY_GET_SUCCESS(true,200,"스터디 그룹 벌금 미정산일 조회에 성공하였습니다."),
    PENALTY_INFO_BY_DATE_GET_SUCCESS(true, 200, "스터디 그룹 해당 날짜 벌금 정산 정보 조회에 성공하였습니다."),
    PENALTY_SETTLED_SUCCESS(true,200,"벌금 정산에 성공하였습니다."),
    PENALTY_SETTLED_CANCEL_SUCCESS(true, 200, "벌금 정산 취소에 성공하였습니다."),
    PENALTY_EXEMPT_SUCCESS(true,200,"벌금 면제에 성공하였습니다."),
    PENALTY_INFO_BY_BETWEEN_DATE_GET_SUCCESS(true, 200, "스터디 그룹 해당 날짜 범위 벌금 정산 정보 조회에 성공하였습니다."),
    PENALTY_COUNTS_GET_SUCCESS(true, 200, "스터디 그룹 멤버별 벌금 횟수 조회에 성공하였습니다."),
    INVITED_MEMBER_STATUS_GET_SUCCESS(true, 200 , "스터디 그룹 초대멤버 응답 상태 조회에 성공하였습니다."),
    SIGN_UP_SUCCESS(true,200,"회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(true,200,"로그안에 성공하였습니다."),
    ACCESS_TOKEN_REISSUE_SUCCESS(true,200, "엑세스 토큰 재발급에 성공하였습니다."),
    CURRENT_USER_GET_SUCCESS(true,200, "현재 로그인 중인 이용자 정보 조회에 성공하였습니다."),
    All_USER_INFO_GET_SUCCESS(true,200,"유저 정보 조회에 성공하였습니다."),
    CHANGE_PASSWORD_SUCCESS(true,200,"비밀번호 변경에 성공하였습니다."),
    CHANGE_END_USER_INFO_SUCCESS(true,200,"사용자 정보 변경에 성공하였습니다."),
    CHANGE_END_USER_PROFILE_IMAGE_SUCCESS(true,200,"사용자 프로필 이미지 변경에 성공하였습니다.");
    private final boolean isSuccess;
    private final int status;
    private final String message;
}
