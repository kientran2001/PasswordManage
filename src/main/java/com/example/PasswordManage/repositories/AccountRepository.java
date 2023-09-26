package com.example.PasswordManage.repositories;

import com.example.PasswordManage.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Iterable<Account> findByUserId(int userId);
}
