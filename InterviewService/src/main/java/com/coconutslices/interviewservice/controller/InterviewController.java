package com.coconutslices.interviewservice.controller;

import com.coconutslices.interviewservice.model.LeetCodeQuestionSnapshotDto;
import com.coconutslices.interviewservice.model.ParsedInterviewDto;
import com.coconutslices.interviewservice.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping
    public ResponseEntity<Void> saveInterview(@RequestBody @Valid ParsedInterviewDto parsedInterviewDto) {
        interviewService.saveInterview(parsedInterviewDto);
        log.info("✅ Interview saved for company: {} | questions: {}",
                parsedInterviewDto.getCompany(),
                parsedInterviewDto.getLeetcodeQuestions().stream().map(LeetCodeQuestionSnapshotDto::getTitle).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
