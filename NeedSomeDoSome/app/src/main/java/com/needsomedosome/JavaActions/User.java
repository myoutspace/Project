package com.example.julien.needsomedosome.JavaActions;

/**
 * Created by Julien on 2017-10-06.
 */

public class User {
    private String username;
    private long pointAmount;
    private String password;
    private String title;

    public User(String username, long pointAmount, String password, String title) {
        this.username = username;
        this.pointAmount = pointAmount;
        this.password = password;
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(long pointAmount) {
        this.pointAmount = pointAmount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
