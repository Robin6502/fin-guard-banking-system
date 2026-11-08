package com.finguard.fraud;

public record RuleResult(String ruleName, int points, String reason) {

    public static RuleResult clear(String ruleName) {
        return new RuleResult(ruleName, 0, "");
    }

    public boolean triggered() {
        return points > 0;
    }
}
