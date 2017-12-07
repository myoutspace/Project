package main.taskmanager.javaActions;

/**
 * Class Task.
 * Used to create a task.
 */

public class Task {

    /**
     * userPost String to represent the name of the user who posted the task
     * pointAmount int to represent the amount of the task
     * tag String to represent the title of the task
     * description String to represent the description of the task
            */

    private String userPost;
    private int pointAmount;
    private String tag;
    private String description;

    /**
     * The constructor
     * @param userPost  The user name who posted the atsk.
     * @param pointAmount  The gpoints of the task.
     * @param tag  The title of the task.
     * @param description   The description of the task.
     */


    public Task(String userPost, int pointAmount, String tag, String description) {
        this.userPost = userPost;
        this.pointAmount = pointAmount;
        this.tag = tag;
        this.description = description;
    }

    /**
     * Get the name of the user who posted the task
     * @return Get the name of the user who posted the task
     */

    public String getUserPost() {
        return userPost;
    }

    /**
     * Set the user name who posted the task
     * @param userPost The user name who posted the task
     */

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }

    /**
     * Get the points of the task
     * @return Get the points of the task
     */

    public int getPointAmount() {
        return pointAmount;
    }

    /**
     * Set the points of the task
     * @param pointAmount  The task description.
     */

    public void setPointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
    }

    /**
     * Get the title of the task
     * @return Get the title of the task
     */
    
    public String getTag() { return tag; }

    /**
     * Set the title of the task
     * @param tag  The task title.
     */

    public void setTag(String tag) { this.tag = tag; }

    /**
     * Get the description of the task
     * @return Get the description of the task
     */

    public String getDescription() { return description; }

    /**
     * Set the description of the task
     * @param description  The task description.
     */

    public void setDescription(String description) { this.description = description; }
}
