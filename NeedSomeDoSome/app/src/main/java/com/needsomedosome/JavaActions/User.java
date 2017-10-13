package com.needsomedosome.JavaActions;

/**
 * Created by Julien on 2017-10-06.
 */

public class User {
    private String username;
    private String pointAmount;
    private String password;
    private String title;
    private String groupName;

    public User(String username, String pointAmount, String password, String title, String groupName) {
        this.username = username;
        this.pointAmount = pointAmount;
        this.password = password;
        this.title = title;
        this.groupName = groupName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(String pointAmount) {
        this.pointAmount = pointAmount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroupName(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
