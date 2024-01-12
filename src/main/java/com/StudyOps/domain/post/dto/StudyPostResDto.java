package com.StudyOps.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyPostResDto {
    private List<StudyPostDto> studyPostDtoList;
    private int total;
}
