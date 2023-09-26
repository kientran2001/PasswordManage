package com.example.PasswordManage.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotBlank(message = "Username cannot be null")
    @Size(min = 1, max = 300)
    private String username;

    private String salt;

    private String hmac;

    public User() {}
    public User(String username, String salt, String hmac) {
        this.username = username;
        this.salt = salt;
        this.hmac = hmac;
    }
}
