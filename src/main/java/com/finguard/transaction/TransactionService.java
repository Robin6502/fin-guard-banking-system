package com.finguard.transaction;

import com.finguard.account.Account;
import com.finguard.account.AccountRepository;
import com.finguard.exception.AccountNotFoundException;
import com.finguard.fraud.Decision;
import com.finguard.fraud.FraudContext;
import com.finguard.fraud.FraudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final AccountRepository accounts;
    private final TransactionRepository transactions;
    private final FraudService fraud;

    public TransactionService(AccountRepository accounts,
                              TransactionRepository transactions,
                              FraudService fraud) {
        this.accounts = accounts;
        this.transactions = transactions;
        this.fraud = fraud;
    }

    @Transactional
    public Transaction deposit(Long accountId, BigDecimal amount) {
        requirePositive(amount);
        Account account = findAccount(accountId);
        account.deposit(amount);
        accounts.save(account);
        return transactions.save(new Transaction(
                accountId, null, TransactionType.DEPOSIT, amount, TransactionStatus.COMPLETED));
    }

    @Transactional
    public Transaction withdraw(Long accountId, BigDecimal amount) {
        requirePositive(amount);
        Account account = findAccount(accountId);
        account.withdraw(amount);
        accounts.save(account);
        return transactions.save(new Transaction(
                accountId, null, TransactionType.WITHDRAWAL, amount, TransactionStatus.COMPLETED));
    }

    @Transactional
    public Transaction transfer(Long fromId, Long toId, BigDecimal amount) {
        requirePositive(amount);
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        Account from = findAccount(fromId);
        Account to = findAccount(toId);

        Decision decision = fraud.assess(new FraudContext(fromId, amount, from.getBalance()));
        if (decision == Decision.REJECT) {
            return transactions.save(new Transaction(
                    fromId, toId, TransactionType.TRANSFER, amount, TransactionStatus.REJECTED));
        }
        if (decision == Decision.FLAG) {
            return transactions.save(new Transaction(
                    fromId, toId, TransactionType.TRANSFER, amount, TransactionStatus.FLAGGED));
        }

        from.withdraw(amount);
        to.deposit(amount);
        accounts.save(from);
        accounts.save(to);
        return transactions.save(new Transaction(
                fromId, toId, TransactionType.TRANSFER, amount, TransactionStatus.COMPLETED));
    }

    public List<Transaction> history(Long accountId) {
        findAccount(accountId); // throws 404 if the account doesn't exist
        return transactions.findByAccountIdOrderByCreatedAtDesc(accountId);
    }

    private Account findAccount(Long accountId) {
        return accounts.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    private void requirePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}