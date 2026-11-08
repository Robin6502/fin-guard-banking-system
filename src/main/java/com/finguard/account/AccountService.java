package com.finguard.account;

import com.finguard.exception.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account create(String ownerName) {
        String number = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        return repository.save(new Account(number, ownerName));
    }

    public Account getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
}