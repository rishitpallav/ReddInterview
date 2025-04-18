package com.coconutslices.redditconsumerservice.model;


public enum Difficulty {

    EASY(1),
    MEDIUM(2),
    HARD(3);

    private final int level;

    Difficulty (int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static Difficulty fromLevel (int level) {
        return switch (level) {
            case 1 -> EASY;
            case 2 -> MEDIUM;
            case 3 -> HARD;
            default -> throw new IllegalArgumentException("❗ Invalid Difficulty Level: " + level);
        };
    }

    public static Difficulty fromString (String level) {
        return switch (level.toLowerCase()) {
            case "easy" -> EASY;
            case "medium" -> MEDIUM;
            case "hard" -> HARD;
            default -> throw new IllegalArgumentException("❗ Invalid Difficulty Level: " + level);
        };
    }
}
