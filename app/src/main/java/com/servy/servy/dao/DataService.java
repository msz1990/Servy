package com.servy.servy.dao;

import android.os.Bundle;

import com.servy.servy.domain.Order;
import com.servy.servy.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shizhema on 16/5/6.
 */
public interface DataService {

    User getUserInfo(String email, String password);
    void insertOrderBundleItem(String itemBundleId, ArrayList<String> itemList);
    void insertOrderItem(String itemBundleId, Bundle bundle);
    List<Order> GetAllOrders();
    List<Order> getOrders(String userId);

}
