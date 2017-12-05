package main.taskmanager.javaActions;

/**
 * Created by ad-ARUKAZAN on 05/12/2017.
 */

public class Resource {

    private String name;
    private String group;

    public Resource(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    ;

    public String getGroup() {
        return group;
    }

    ;

    public void setName(String name) {
        this.name = name;
    }

    ;

    public void setGroup(String group) {
        this.group = group;
    }

    ;
}
