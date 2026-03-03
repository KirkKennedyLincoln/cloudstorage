package com.udacity.jwdnd.course1.cloudstorage.models;

public class Credential {
    private Integer credentialid;
    private String url;
    private String username;
    private String credentialkey;
    private String password;
    private Integer userid;

    public Integer getCredentialid() {
        return credentialid;
    }
    public String getCredentialkey() {
        return credentialkey;
    }
    public String getPassword() {
        return password;
    }
    public String getUrl() {
        return url;
    }
    public Integer getUserid() {
        return userid;
    }
    public String getUsername() {
        return username;
    }
    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }
    public void setCredentialkey(String credentialkey) {
        this.credentialkey = credentialkey;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
