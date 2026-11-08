package com.finguard.fraud;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HighAmountRuleTest {

    private final HighAmountRule rule = new HighAmountRule();

    private int pointsFor(String amount) {
        return rule.evaluate(new FraudContext(1L, new BigDecimal(amount))).points();
    }

    @Test
    void smallAmountScoresZero() {
        assertEquals(0, pointsFor("100"));
    }

    @Test
    void highAmountScoresFlagLevel() {
        assertEquals(40, pointsFor("5000"));
    }

    @Test
    void veryHighAmountScoresRejectLevel() {
        assertEquals(80, pointsFor("10000"));
    }
}