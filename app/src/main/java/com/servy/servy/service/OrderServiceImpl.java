package com.servy.servy.service;

import android.os.Bundle;

import com.servy.servy.dao.DataService;
import com.servy.servy.dao.DataServiceImpl;
import com.servy.servy.domain.Order;
import com.servy.servy.utils.ListViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shizhema on 16/5/23.
 */
public class OrderServiceImpl implements OrderService {
    private DataService dataService;
    @Override
    public void postOrder(String itemBundleId, Bundle bundle) {
        dataService=new DataServiceImpl();
        dataService.insertOrderBundleItem(itemBundleId,bundle.getStringArrayList("orderItems"));
        dataService.insertOrderItem(itemBundleId,bundle);
    }

    @Override
    public List<HashMap<String, Object>>  getDeliverOrders(String userId) {
        dataService=new DataServiceImpl();
        List<Order> orders=dataService.GetAllOrders();
        List<HashMap<String, Object>> res=new ArrayList<>();
        HashMap<String, Object> map=null;
        for(Order order:orders){
            if(order.getUserId().equals(userId)) continue;
            try {
                map= ListViewUtils.Object2HashMap(order);
                map.put("orderTotal",order.getOrderTotal());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            res.add(map);
        }
        return res;
    }

    @Override
    public List<HashMap<String, Object>> getOrders(String userId) {
        dataService=new DataServiceImpl();
        List<Order> orders=dataService.getOrders(userId);
        List<HashMap<String, Object>> res=new ArrayList<>();
        HashMap<String, Object> map=null;
        for(Order order:orders){
            try {
                map= ListViewUtils.Object2HashMap(order);
                map.put("orderTotal",order.getOrderTotal());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            res.add(map);
        }
        return res;
    }


}
