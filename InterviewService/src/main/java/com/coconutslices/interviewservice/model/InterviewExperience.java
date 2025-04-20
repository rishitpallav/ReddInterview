package com.coconutslices.interviewservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "interview_leetcode_questions", joinColumns = @JoinColumn(name = "interview_id"))
    private List<LeetCodeQuestionSnapshot> leetcodeQuestions;

    @Enumerated(EnumType.STRING)
    private Outcome outcome;

    private String title;

    @Column(length = 10000)
    private String content;

    private String author;
    private String url;

    @Column(name = "posted_date", columnDefinition = "TIMESTAMP")
    private Instant postedDate;
}