package com.needsomedosome.JavaActions;

/**
 * Created by Julien on 2017-10-06.
 */

public class Task {
    private String userPost;
    private String userResolve;
    private int pointAmount;
    private String completionDate;

    public Task(String userPost, String userResolve, int pointAmount, String completionDate) {
        this.userPost = userPost;
        this.userResolve = userResolve;
        this.pointAmount = pointAmount;
        this.completionDate = completionDate;
    }

    public String getUserPost() {
        return userPost;
    }

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }

    public String getUserResolve() {
        return userResolve;
    }

    public void setUserResolve(String userResolve) {
        this.userResolve = userResolve;
    }

    public int getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(int pointAmount) {
        this.pointAmount = pointAmount;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }
}
