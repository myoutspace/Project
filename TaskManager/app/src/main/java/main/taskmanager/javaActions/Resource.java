package main.taskmanager.javaActions;

/**
 * Class Resource.
 * Used to create resources for a task.
 */

public class Resource {

    /**
     * name String to represent the name of the resource
     * group String to represent the resource's group
     */

    private String name;
    private String group;

    /**
     * The constructor
     * @param name  The resource's name.
     * @param group   The group name.
     */

    public Resource(String name, String group) {
        this.name = name;
        this.group = group;
    }

    /**
     * Get the name of the resource
     * @return Get the name of the resource.
     */


    public String getName() {
        return name;
    }

    /**
     * Get the resource's group
     * @return Get the resource's group.
     */

    public String getGroup() {
        return group;
    }

    /**
     * Set the resource's name.
     * @param name  The resource's name.
     */


    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the resource's group.
     * @param group  resource's group.
     */

    public void setGroup(String group) {
        this.group = group;
    }

}
