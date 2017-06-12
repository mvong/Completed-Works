package itp341.vong.mark.finalproject.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mark on 5/5/17.
 */

public class Post implements Serializable {
    private User mainUser;
    private String description;
    private String time;
    private Location location;
    private ArrayList<Comment> comments;
    private String title;
    private boolean offer;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    private Drawable image;

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Post(String title, User mainUser, String description, String time, Location location, boolean offer) {
        this.title = title;
        this.mainUser = mainUser;
        this.description = description;
        this.time = time;
        this.location = location;
        this.offer = offer;

    }

    public Post() {}
}
