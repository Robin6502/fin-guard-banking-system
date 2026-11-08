package com.finguard.account;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    public record CreateAccountRequest(String ownerName) {}

    @PostMapping
    public Account create(@RequestBody CreateAccountRequest request) {
        return service.create(request.ownerName());
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable Long id) {
        return service.getById(id);
    }
}