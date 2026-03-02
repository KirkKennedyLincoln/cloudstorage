package com.udacity.jwdnd.course1.cloudstorage.models;

public class User {
    private Integer userid;
    private String
        username,
        password,
        salt,
        firstname,
        lastname;

    public User() {}

    public User(Integer userid, String username, String salt, String password, String firstName, String lastName) {
        this.userid = userid;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstname = firstName;
        this.lastname = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }
    
    public Integer getUserid() {
        return userid;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
