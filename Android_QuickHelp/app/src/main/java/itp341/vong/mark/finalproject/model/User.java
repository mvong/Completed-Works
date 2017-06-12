package itp341.vong.mark.finalproject.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mark on 5/5/17.
 */

public class User implements Serializable {

    private String username;
    private String password;
    private String location;
    private String email;
    private int age;


    private ArrayList<Post> myPosts;

    public ArrayList<Post> getMyPosts() {
        return myPosts;
    }

    public void setMyPosts(ArrayList<Post> myPosts) {
        this.myPosts = myPosts;
    }

    public void addPosts(Post post) {
        this.myPosts.add(post);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.myPosts = new ArrayList<Post>();
    }

    public void setEmail(String email) {
        this.email = email;

    }
}
