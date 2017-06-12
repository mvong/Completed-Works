package itp341.vong.mark.finalproject.model;

import java.io.Serializable;

/**
 * Created by Mark on 5/5/17.
 */

public class Comment implements Serializable {
    private String user;
    private String comment;
    private String time;

    public Comment(String user, String comment, String time) {
        this.user = user;
        this.comment = comment;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
