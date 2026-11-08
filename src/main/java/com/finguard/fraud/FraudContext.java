package com.finguard.fraud;

import java.math.BigDecimal;

public record FraudContext(Long accountId, BigDecimal amount, BigDecimal balance) {}