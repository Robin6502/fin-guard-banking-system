package com.finguard.transaction;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    private Long targetAccountId; // only used for transfers

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected Transaction() {} // required by JPA

    public Transaction(Long accountId, Long targetAccountId, TransactionType type,
                       BigDecimal amount, TransactionStatus status) {
        this.accountId = accountId;
        this.targetAccountId = targetAccountId;
        this.type = type;
        this.amount = amount;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getAccountId() { return accountId; }
    public Long getTargetAccountId() { return targetAccountId; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public TransactionStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}