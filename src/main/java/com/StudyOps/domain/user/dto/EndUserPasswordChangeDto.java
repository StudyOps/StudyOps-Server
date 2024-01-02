package com.StudyOps.domain.user.dto;

import lombok.Getter;

@Getter
public class EndUserPasswordChangeDto {
    private String oldPassword;
    private String newPassword;
}
