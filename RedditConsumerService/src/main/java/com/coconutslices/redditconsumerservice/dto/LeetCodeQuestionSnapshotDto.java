package com.coconutslices.redditconsumerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeetCodeQuestionSnapshotDto {
    private int id;
    private String title;
    private String difficulty;
    private String url;
}
