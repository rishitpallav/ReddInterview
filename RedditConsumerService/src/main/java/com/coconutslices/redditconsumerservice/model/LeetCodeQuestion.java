package com.coconutslices.redditconsumerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeetCodeQuestion {
    private int id;
    private String name;
    private Difficulty difficulty;
    private String url;
}
