package com.servy.servy.domain;

import java.io.Serializable;

/**
 * Created by shizhema on 16/5/24.
 */
public class BundleItem implements Serializable{
    private String id;
    private String itemsbundleid;
    private String text;
    private String datetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemsbundleid() {
        return itemsbundleid;
    }

    public void setItemsbundleid(String itemsbundleid) {
        this.itemsbundleid = itemsbundleid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}
