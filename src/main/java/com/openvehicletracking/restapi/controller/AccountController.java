package com.openvehicletracking.restapi.controller;


import com.openvehicletracking.restapi.model.Account;
import com.openvehicletracking.restapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Account> getAccounts() {
        User principle = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findAccountsByOwner(principle.getUsername());
    }


    @RequestMapping(method = RequestMethod.POST)
    public Account save(@RequestBody Account account) {
        User principle = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        account.setOwner(principle.getUsername());
        accountRepository.save(account);
        return account;
    }



}
