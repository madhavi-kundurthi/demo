package com.example.demo.bean;


import java.io.Serializable;
public class UsersBean implements Serializable {

    private String user;
    private String password;
    private String token;
    private String gameid;

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsersBean{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", gameid='" + gameid + '\'' +
                '}';
    }
}
