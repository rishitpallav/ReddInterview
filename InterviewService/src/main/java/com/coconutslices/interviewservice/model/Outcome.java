package com.coconutslices.interviewservice.model;

public enum Outcome {

    FAIL(0),
    PASS(1),
    UNKNOWN(2);

    private final int result;

    Outcome (int result) { this.result = result; }

    public int getResult() { return result; }

    public static Outcome fromResult (int result) {
        return switch (result) {
            case 0 -> FAIL;
            case 1 -> PASS;
            case 2 -> UNKNOWN;
            default -> throw new IllegalArgumentException("❗ Invalid Outcome Result: " + result);
        };
    }

    public static Outcome fromString (String result) {
        return switch (result.toLowerCase()) {
            case "fail" -> FAIL;
            case "pass" -> PASS;
            case "unknown" -> UNKNOWN;
            default -> throw new IllegalArgumentException("❗ Invalid Outcome String: " + result);
        };
    }
}
