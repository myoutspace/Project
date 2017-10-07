package main.taskmanager.javaActions;

import java.util.LinkedList;


/**
 * Created by Julien on 2017-10-06.
 */

public class Group {
    private LinkedList<User> userList;
    private String name;
    private String startingPointAmount;

    public Group(LinkedList<User> userList, String name, String startingPointAmount) {
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

    public String getStartingPointAmount() {
        return startingPointAmount;
    }

    public void setStartingPointAmount(String startingPointAmount) {
        this.startingPointAmount = startingPointAmount;
    }
}
