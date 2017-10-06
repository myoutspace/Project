package com.example.julien.needsomedosome.JavaActions;

import java.util.LinkedList;

/**
 * Created by Julien on 2017-10-06.
 */

public class Group {
    private LinkedList<User> userList;
    private String name;
    private int startingPointAmount;

    public Group(LinkedList<User> userList, String name, int startingPointAmount) {
        this.userList = userList;
        this.name = name;
        this.startingPointAmount = startingPointAmount;
    }

    public LinkedList<User> getUserList() {
        return userList;
    }

    public void setUserList(LinkedList<User> userList) {
        this.userList = userList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartingPointAmount() {
        return startingPointAmount;
    }

    public void setStartingPointAmount(int startingPointAmount) {
        this.startingPointAmount = startingPointAmount;
    }
}
