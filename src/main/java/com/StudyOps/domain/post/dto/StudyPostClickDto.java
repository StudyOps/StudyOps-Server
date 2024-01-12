package com.StudyOps.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyPostClickDto {
    private String title;
    private String contents;
    private String writer;
    private String email;
    private LocalDate date;
    private List<String> urls = new ArrayList<>();
}
