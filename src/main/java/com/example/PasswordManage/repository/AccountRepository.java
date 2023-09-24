package com.example.PasswordManage.repository;

import com.example.PasswordManage.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Iterable<Account> findByUserId(int userId);
}
