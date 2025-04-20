package com.coconutslices.interviewservice.service;

import com.coconutslices.interviewservice.model.*;
import com.coconutslices.interviewservice.repository.InterviewExperienceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewExperienceRepository interviewExperienceRepository;

    @Transactional
    public void saveInterview(ParsedInterviewDto parsedInterviewDto) {
        InterviewExperience interviewExperience = InterviewExperience.builder()
                .company(parsedInterviewDto.getCompany())
                .outcome(Outcome.fromString(parsedInterviewDto.getOutcome()))
                .title(parsedInterviewDto.getTitle())
                .content(parsedInterviewDto.getContent())
                .author(parsedInterviewDto.getAuthor())
                .url(parsedInterviewDto.getUrl())
                .postedDate(parsedInterviewDto.getPostedDate())
                .leetcodeQuestions(mapQuestions(parsedInterviewDto))
                .build();

        try {
            InterviewExperience returnedExperience = interviewExperienceRepository.save(interviewExperience);
            log.info("✅ Interview Experience with id: {} saved successfully", returnedExperience.getId());
        } catch (DataIntegrityViolationException e) {
            log.error("❌ Error saving entity: Data Integrity Violation: " + e.getMessage());
        } catch (DataAccessException e) {
            log.error("❌ Error saving entity: Data Access Error Occurred: " + e.getMessage());
        } catch (Exception e) {
            log.error("❌ An unexpected error occurred during save: " + e.getMessage());
        }
    }

    private List<LeetCodeQuestionSnapshot> mapQuestions(ParsedInterviewDto parsedInterviewDto) {
        return parsedInterviewDto.getLeetcodeQuestions().stream().map(q ->
            LeetCodeQuestionSnapshot.builder()
                    .id(q.getId())
                    .title(q.getTitle())
                    .difficulty(Difficulty.fromString(q.getDifficulty()))
                    .url(q.getUrl())
                    .build()
        ).collect(Collectors.toList());
    }

}
