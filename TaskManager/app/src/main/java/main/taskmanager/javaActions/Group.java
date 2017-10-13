package main.taskmanager.javaActions;

import java.util.LinkedList;


/**
 * Created by Julien on 2017-10-06.
 */

public class Group {
    private LinkedList<User> userList;
    private String name;

    public Group(String name) {
        this.userList = null;
        this.name = name;
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
}
