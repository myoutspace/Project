package main.taskmanager.javaActions;

/**
 * Class DataResource.
 * Used to create both name a checkbox value for a resource.
 */

public class DataResource {
    /**
     * name String to represent the name of the resource
     * check boolean to represent the chcekbox value
     */

    public String name;
    public boolean checked;

    /**
     * The constructor
     * @param name  The resource's name.
     * @param checked   The checkbox value.
     */

    public DataResource(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }
}
