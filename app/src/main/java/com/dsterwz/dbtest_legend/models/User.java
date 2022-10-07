package com.dsterwz.dbtest_legend.models;

public class User {

    private String email;

    private String password;

    private String login;

    public User(String email, String password, String login) {
        this.email = email;
        this.password = password;
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
