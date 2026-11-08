package com.finguard.transaction;

import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts/{accountId}")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    public record AmountRequest(BigDecimal amount) {}

    @PostMapping("/deposit")
    public Transaction deposit(@PathVariable Long accountId, @RequestBody AmountRequest request) {
        return service.deposit(accountId, request.amount());
    }

    @PostMapping("/withdraw")
    public Transaction withdraw(@PathVariable Long accountId, @RequestBody AmountRequest request) {
        return service.withdraw(accountId, request.amount());
    }
    @GetMapping("/transactions")
    public List<Transaction> history(@PathVariable Long accountId) {
        return service.history(accountId);
    }

    public record TransferRequest(Long targetAccountId, BigDecimal amount) {}

    @PostMapping("/transfer")
    public Transaction transfer(@PathVariable Long accountId, @RequestBody TransferRequest request) {
        return service.transfer(accountId, request.targetAccountId(), request.amount());
    }
}