package main.taskmanager.javaActions;

import java.util.List;


/**
 * Created by Julien on 2017-10-06.
 */

public class Group {
    private List<User> userList;
    private String groupName;

    public Group(String name) {
        this.userList = null;
        this.groupName = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addUser (User user) { this.userList.add(user); }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
