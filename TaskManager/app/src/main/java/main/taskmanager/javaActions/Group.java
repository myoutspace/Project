package main.taskmanager.javaActions;

import java.util.List;


/**
 * Class Group.
 * Used to create a group.
 */

public class Group {

    /**
     * userList List<User> to represent list of users in the group
     * groupName String to represent the group name
     */

    private List<User> userList;
    private String groupName;

    public Group(String name) {

        /**
         * The constructr
         * @param name  The users list
         * @param groupName   The group name.
         */

        this.userList = null;
        this.groupName = name;
    }

    /**
     * Get the users list
     * @return Get the users list
     */

    public List<User> getUserList() {
        return userList;
    }

    /**
     * Set the list of users
     * @param userList  The list of users
     */

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * Add a user to the list
     * @param user  The user
     */

    public void addUser (User user) { this.userList.add(user); }

    /**
     * Get the groupName
     * @return Get the users list
     */

    public String getGroupName() {
        return groupName;
    }

    /**
     * Set the group name.
     * @param groupName  The group nae
     */

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
