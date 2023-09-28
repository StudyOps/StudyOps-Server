package com.StudyOps.domain.member.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class InvitedMemberReqDto {
    private  List<String> invitees;
}
