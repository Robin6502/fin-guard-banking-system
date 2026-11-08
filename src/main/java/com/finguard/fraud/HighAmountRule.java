package com.finguard.fraud;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HighAmountRule implements FraudRule {

    private static final BigDecimal HIGH = new BigDecimal("5000");
    private static final BigDecimal VERY_HIGH = new BigDecimal("10000");

    @Override
    public RuleResult evaluate(FraudContext ctx) {
        if (ctx.amount().compareTo(VERY_HIGH) >= 0) {
            return new RuleResult("HIGH_AMOUNT", 80, "Amount is 10000 or more");
        }
        if (ctx.amount().compareTo(HIGH) >= 0) {
            return new RuleResult("HIGH_AMOUNT", 40, "Amount is 5000 or more");
        }
        return RuleResult.clear("HIGH_AMOUNT");
    }
}