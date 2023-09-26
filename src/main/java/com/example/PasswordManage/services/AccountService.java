package com.example.PasswordManage.services;

import com.example.PasswordManage.models.Account;
import com.example.PasswordManage.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account createAccount(int userId, String acc, String pass, String web) {
        Account account = new Account(userId, acc, pass, web);
        return accountRepository.save(account);
    }
}
