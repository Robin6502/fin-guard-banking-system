package com.finguard.fraud;

public interface FraudRule {
    RuleResult evaluate(FraudContext context);
}