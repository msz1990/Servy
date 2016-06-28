package com.servy.servy.service;


import com.servy.servy.dao.DataService;
import com.servy.servy.dao.DataServiceImpl;
import com.servy.servy.domain.User;

/**
 * Created by shizhema on 16/5/6.
 */
public class UserServiceImpl implements UserService {

    private DataService dataService;
    @Override
    public User getUserInfo(String email, String password) {
        dataService=new DataServiceImpl();
        return dataService.getUserInfo(email,password);
    }
}
