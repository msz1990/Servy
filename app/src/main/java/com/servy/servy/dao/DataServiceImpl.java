package com.servy.servy.dao;

import android.os.Bundle;

import com.servy.servy.domain.Order;
import com.servy.servy.domain.User;
import com.servy.servy.service.HttpService;
import com.servy.servy.service.HttpServiceImpl;
import com.servy.servy.utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shizhema on 16/5/6.
 */
public class DataServiceImpl implements DataService{
    private final String SERVERAPI="http://www.qpraxis.com/servy/Servy_API_v1.0.php";
    @Override
    public User getUserInfo(String email, String password) {
        String condition="FUNC=Login&&email="+email+"&&password="+password;
        HttpService httpService=new HttpServiceImpl();
        User user=null;
        try {
            JSONObject jsonObject = httpService.getJSONObject(SERVERAPI,condition).getJSONArray("login").getJSONObject(0);
            user= JSONUtils.json2Object(jsonObject, User.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void insertOrderBundleItem(String itemBundleId, ArrayList<String> itemList) {
        //JSONArray jsonArray=new JSONArray(itemList);
        String condition="FUNC=InsertOrderBundleItem&&itemsbundleid="+itemBundleId+"&&text="+itemList.toString();
        HttpService httpService=new HttpServiceImpl();
        httpService.post(SERVERAPI,condition);
    }

    @Override
    public void insertOrderItem(String itemBundleId, Bundle bundle) {
        User user= (User) bundle.get("user");
        String condition="FUNC=InsertOrderItem&&userid="+user.getId()+"&&title="+bundle.getString("orderTitle")+
                "&&phonenumber="+bundle.getString("phoneNumber")+"&&imagepath="+user.getImagepath()+
                "&&address=" +bundle.getString("address")+"&&lat="+bundle.getString("lat")+"&&long="+bundle.getString("long")+
                "&&city="+bundle.getString("city")+"&&state="+bundle.getString("state")+"&&zip="+bundle.getString("zip")+
                "&&estimatedprice="+bundle.getString("estimateTotal")+"&&tip="+bundle.getString("tip")+
                "&&deliverby="+bundle.getString("deliverTime")+"&&itemsbundleid="+itemBundleId+
                "&&category="+bundle.getString("category");
        HttpService httpService=new HttpServiceImpl();
        httpService.post(SERVERAPI,condition);
    }

    @Override
    public List<Order> GetAllOrders() {
        String condition="FUNC=GetAllOrders";
        HttpService httpService=new HttpServiceImpl();
        Order order=null;
        List<Order> list=new ArrayList<>();
        try {
            JSONArray jsonArray = httpService.getJSONObject(SERVERAPI, condition).getJSONArray("allorders");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                order= JSONUtils.json2Object(jsonObject,Order.class);
                list.add(order);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Order> getOrders(String userId) {
        String condition="FUNC=GetOrdersForUser&&userid="+userId;
        HttpService httpService=new HttpServiceImpl();
        Order order=null;
        List<Order> list=new ArrayList<>();
        try {
            JSONArray jsonArray = httpService.getJSONObject(SERVERAPI, condition).getJSONArray("userorders");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                order= JSONUtils.json2Object(jsonObject,Order.class);
                list.add(order);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


}
