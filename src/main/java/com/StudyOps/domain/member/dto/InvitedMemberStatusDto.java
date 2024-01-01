package com.StudyOps.domain.member.dto;

import com.StudyOps.domain.member.entity.AcceptStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InvitedMemberStatusDto {
    private String nickName;
    private String profileImageUrl;
    private AcceptStatus status;
}
