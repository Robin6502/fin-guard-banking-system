package com.finguard.fraud;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudService {

    private final List<FraudRule> rules;   // Spring injects every FraudRule it finds

    public FraudService(List<FraudRule> rules) {
        this.rules = rules;
    }

    public Decision assess(FraudContext context) {
        int score = rules.stream()
                .map(rule -> rule.evaluate(context))
                .mapToInt(RuleResult::points)
                .sum();

        if (score >= 80) return Decision.REJECT;
        if (score >= 40) return Decision.FLAG;
        return Decision.APPROVE;
    }
}