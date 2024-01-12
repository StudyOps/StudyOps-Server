package com.StudyOps.domain.post.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyPostDto {
    private Long postId;
    private String title;
    private String writer;
    private LocalDate date;
}
