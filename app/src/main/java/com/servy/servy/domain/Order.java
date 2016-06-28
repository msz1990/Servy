package com.servy.servy.domain;

import java.util.List;

/**
 * Created by shizhema on 16/5/24.
 * SELECT F.id, F.userid, U.username, U.imagepath as userimage, F.imagepath as image, U2.id as delivererid,
 * U2.username as deliverername, U2.imagepath as delivererimage, F.title, F.datetime, F.address, F.latitude,
 * F.longitude, F.city, F.state, F.zip, F.estimatedprice, F.tip, F.deliverby, F.itemsbundleid, F.flagged, F.category,
 * F.status, (select count(*) from OrderItems where itemsbundleid = 'F.itemsbundleid') as itemscount
 */
public class Order {
    private String id;
    private String userId;
    private String userName;
    private String userImage;
    private String image;
    private String delivererId;
    private String delivererName;
    private String delivererImage;
    private String title;
    private String dateTime;
    private String address;
    private String latitude;
    private String longitude;
    private String city;
    private String state;
    private String zip;
    private String estimatedPrice;
    private String tip;
    //private String orderTotal;
    private String deliverBy;
    private String itemsBundleId;
    private String flagged;
    private String category;
    private String status;
    private String itemsCount;
    private List<BundleItem> orderItems;

    public String getOrderTotal() {
        return Integer.parseInt(estimatedPrice)+Integer.parseInt(tip)+"";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(String delivererId) {
        this.delivererId = delivererId;
    }

    public String getDelivererName() {
        return delivererName;
    }

    public void setDelivererName(String delivererName) {
        this.delivererName = delivererName;
    }

    public String getDelivererImage() {
        return delivererImage;
    }

    public void setDelivererImage(String delivererImage) {
        this.delivererImage = delivererImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(String estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }


    public String getFlagged() {
        return flagged;
    }

    public void setFlagged(String flagged) {
        this.flagged = flagged;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliverBy() {
        return deliverBy;
    }

    public void setDeliverBy(String deliverBy) {
        this.deliverBy = deliverBy;
    }

    public String getItemsBundleId() {
        return itemsBundleId;
    }

    public void setItemsBundleId(String itemsBundleId) {
        this.itemsBundleId = itemsBundleId;
    }

    public String getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(String itemsCount) {
        this.itemsCount = itemsCount;
    }
}
