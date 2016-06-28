package com.servy.servy.service;


import com.servy.servy.domain.User;

/**
 * Created by shizhema on 16/5/6.
 */
public interface UserService {

    User getUserInfo(String email, String password);
}
