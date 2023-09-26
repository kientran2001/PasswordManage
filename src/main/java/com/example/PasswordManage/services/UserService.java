package com.example.PasswordManage.services;

import com.example.PasswordManage.models.User;
import com.example.PasswordManage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User createUser(String username, String password) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String hmac = HmacService.calculateHMAC(password, salt.toString());

        User user = new User(username, salt.toString(), hmac);
        return userRepository.save(user);
    }
}
