package com.finguard.fraud;

import com.finguard.transaction.TransactionRepository;
import com.finguard.transaction.TransactionType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class VelocityRule implements FraudRule {

    private final TransactionRepository transactions;

    public VelocityRule(TransactionRepository transactions) {
        this.transactions = transactions;
    }

    @Override
    public RuleResult evaluate(FraudContext ctx) {
        Instant since = Instant.now().minus(10, ChronoUnit.MINUTES);
        long recent = transactions.countByAccountIdAndTypeAndCreatedAtAfter(
                ctx.accountId(), TransactionType.TRANSFER, since);

        if (recent >= 5) {
            return new RuleResult("VELOCITY", 40, recent + " transfers in the last 10 minutes");
        }
        return RuleResult.clear("VELOCITY");
    }
}