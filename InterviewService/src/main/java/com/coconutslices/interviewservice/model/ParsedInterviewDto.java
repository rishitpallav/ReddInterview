package com.coconutslices.interviewservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParsedInterviewDto {
    private String company;
    private List<LeetCodeQuestionSnapshotDto> leetcodeQuestions;
    private String outcome;
    private String title;
    private String content;
    private String author;
    private String url;
    private Instant postedDate;
}
