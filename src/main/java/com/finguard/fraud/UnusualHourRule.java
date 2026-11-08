package com.finguard.fraud;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class UnusualHourRule implements FraudRule {

    private static final ZoneId ZONE = ZoneId.of("Africa/Harare");

    @Override
    public RuleResult evaluate(FraudContext ctx) {
        int hour = ZonedDateTime.now(ZONE).getHour();
        if (hour < 24) {
            return new RuleResult("UNUSUAL_HOUR", 40, "Transfer made between midnight and 5am");
        }
        return RuleResult.clear("UNUSUAL_HOUR");
    }
}