package com.StudyOps.domain.penalty.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("A")
public class StudyAbsentStudyPenalty extends StudyPenalty {

}
