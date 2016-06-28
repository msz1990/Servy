package com.servy.servy.domain;

import java.io.Serializable;

/**
 * Created by shizhema on 16/5/6.
 */
public class User implements Serializable{
    private String count;
    private String id;
    private String email;
    private String imagepath;
    private String username;
    private String datetime;

    public String getCount() {
        return count;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setId(String id) {
        this.id = id;
    }
}
