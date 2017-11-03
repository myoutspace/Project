package main.taskmanager.javaActions;

/**
 * Created by Julien on 2017-10-06.
 */

public class Task {
    private String userPost;
    private int pointAmount;
    private String tag;
    private String description;

    public Task(String userPost, int pointAmount, String tag, String description) {
        this.userPost = userPost;
        this.pointAmount = pointAmount;
        this.tag = tag;
        this.description = description;
    }

    public String getUserPost() {
        return userPost;
    }

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }

    public int getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
    }
    
    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
