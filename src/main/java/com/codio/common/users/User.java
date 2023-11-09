package com.codio.common.users;

import com.codio.helpers.Driver;

public class User {

    protected String login;
    protected String password;
    protected String email;
    protected String fullName;

    public String getLogin() {
        if (login == null) {
            Driver.exitTestWithError("Login was not set for user");
        }
        return login;
    }

    public String getPassword() {
        if (password == null) {
            Driver.exitTestWithError("Password was not set for user");
        }
        return password;
    }

    public String getEmail() {
        if (email == null) {
            Driver.exitTestWithError("Email was not set for user");
        }
        return email;
    }

    public String getFullName() {
        if (fullName == null) {
            Driver.exitTestWithError("Full name was not set for user");
        }
        return fullName;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
