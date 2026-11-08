package com.finguard.fraud;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BalanceDrainRule implements FraudRule {

    private static final BigDecimal DRAIN_SHARE = new BigDecimal("0.90");

    @Override
    public RuleResult evaluate(FraudContext ctx) {
        if (ctx.balance().signum() <= 0) {
            return RuleResult.clear("BALANCE_DRAIN");
        }
        BigDecimal threshold = ctx.balance().multiply(DRAIN_SHARE);
        if (ctx.amount().compareTo(threshold) >= 0) {
            return new RuleResult("BALANCE_DRAIN", 30, "Transfer is 90% or more of the account balance");
        }
        return RuleResult.clear("BALANCE_DRAIN");
    }
}