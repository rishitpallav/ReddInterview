package com.coconutslices.interviewservice.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeetCodeQuestionSnapshot {
    private int id;
    private String title;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    private String url;
}