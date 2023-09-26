package com.example.PasswordManage.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    private String acc;

    private String pass;

    private String web;

    public Account(){}

    public Account(int userId, String acc, String pass, String web) {
        this.userId = userId;
        this.acc = acc;
        this.pass = pass;
        this.web = web;
    }
}
