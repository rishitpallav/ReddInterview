package com.coconutslices.redditconsumerservice.service;

import com.coconutslices.redditconsumerservice.dto.LeetCodeQuestionSnapshotDto;
import com.coconutslices.redditconsumerservice.dto.ParsedInterviewDto;
import com.coconutslices.redditconsumerservice.model.LeetCodeQuestion;
import com.coconutslices.redditconsumerservice.model.RedditPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostParserService {

    private final CompanyListLoader companyListLoader;
    private final LeetCodeQuestionLoader leetCodeQuestionLoader;

    public ParsedInterviewDto parse(RedditPost post) {
        String content = normalize(post.getContent());

        String matchingCompany = extractCompany(content);
        List<LeetCodeQuestionSnapshotDto> matchedQuestions = extractQuestion(content);
        String outcome = extractOutcome(content);

        return ParsedInterviewDto.builder()
                .company(matchingCompany)
                .leetcodeQuestions(matchedQuestions)
                .outcome(outcome)
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .url(post.getUrl())
                .postedDate(Instant.ofEpochSecond(post.getCreatedUtc()))
                .build();
    }

    public String extractCompany(String content) {
        Set<String> companies = companyListLoader.getCompanies();

        for (String company : companies) {
            if (containsExactWord(content, company)) {
                return company;
            }
        }

        return null;
    }

    public List<LeetCodeQuestionSnapshotDto> extractQuestion(String content) {
        List<LeetCodeQuestionSnapshotDto> matchedQuestions = new ArrayList<>();

        for (LeetCodeQuestion question : leetCodeQuestionLoader.getLeetCodeQuestions()) {
            if (content.contains(question.getName())) {
                matchedQuestions.add(
                        LeetCodeQuestionSnapshotDto.builder()
                                .id(question.getId())
                                .title(question.getName())
                                .difficulty(question.getDifficulty().name().toLowerCase())
                                .url(question.getUrl())
                                .build()
                );
            }
        }

        return matchedQuestions;
    }

    public String extractOutcome(String content) {
        if (containsExactWord(content, "offer") || containsExactWord(content, "got in") || containsExactWord(content, "accepted")) {
            return "pass";
        } else if (containsExactWord(content, "rejected") || containsExactWord(content, "didn't make it") || containsExactWord(content, "ghosted")) {
            return "fail";
        } else {
            return "unknown";
        }
    }

    public String normalize(String raw) {
        return raw == null ? "" : raw
                .toLowerCase()
                .replaceAll("\\W+", " ")
                .trim();
    }

    private boolean containsExactWord(String content, String word) {
        String regex = "\\b" + Pattern.quote(word) + "\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        return matcher.find();
    }
}
