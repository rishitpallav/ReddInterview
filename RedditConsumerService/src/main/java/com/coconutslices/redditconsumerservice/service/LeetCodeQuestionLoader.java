package com.coconutslices.redditconsumerservice.service;

import com.coconutslices.redditconsumerservice.model.Difficulty;
import com.coconutslices.redditconsumerservice.model.LeetCodeQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class LeetCodeQuestionLoader {

    private final Set<LeetCodeQuestion> leetCodeQuestions = new HashSet<>();

    public LeetCodeQuestionLoader(@Value("${file-path.leetcode-questions}") String questionsFilePath) {
        try {
            InputStream inputStream = new ClassPathResource(questionsFilePath).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Skipping the CSV header
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                String[] question = line.split("\\|");

                if (question.length < 4) {
                    log.warn("⚠️ Skipping invalid line : {}", line);
                    continue;
                }

                LeetCodeQuestion leetCodeQuestion = new LeetCodeQuestion(
                        Integer.parseInt(question[0].trim()),
                        question[1].trim(),
                        Difficulty.fromString(question[2].trim()),
                        question[3].trim()
                );

                leetCodeQuestions.add(leetCodeQuestion);
            }

            reader.close();

            log.info("✅ Loaded {} LeetCode questions from {}", leetCodeQuestions.size(), questionsFilePath);
        } catch (Exception e) {
            log.error("❗ Error loading leetcode questions from {} : {}", questionsFilePath, e.getMessage());
            log.info("⚠️ Loaded only {} leetcode questions", leetCodeQuestions.size());
        }
    }

    public Set<LeetCodeQuestion> getLeetCodeQuestions() {
        return leetCodeQuestions;
    }
}
