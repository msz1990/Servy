package com.servy.servy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.servy.servy.R;
import com.servy.servy.domain.User;
import com.servy.servy.service.UserService;
import com.servy.servy.service.UserServiceImpl;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shizhema on 16/5/20.
 */
public class SplashActivity extends Activity{
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    public void init(){
        Timer time=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                sp=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                boolean firstLogin=sp.getBoolean("firstLogin", true);
                if(firstLogin){
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putBoolean("firstLogin",false);
                    editor.putBoolean("showLogin", true);
                    editor.commit();
                    Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    String email=sp.getString("email","");
                    String password=sp.getString("password","");
                    UserExsist userExsist=new UserExsist();
                    userExsist.execute(email,password);
                }
            }
        };
        time.schedule(task,3000);

    }

    private class UserExsist extends AsyncTask<String, Integer, Boolean> {

        private Intent intent=null;
        private String email;
        private String password;
        @Override
        protected Boolean doInBackground(String... params) {
            email=params[0];
            password=params[1];
            return isUserExsist(email,password);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("email",email);
                editor.putString("password", password);
                editor.commit();
                startActivity(intent);
            }else{
                intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            SplashActivity.this.finish();

        }

        private UserService userService;
        public boolean isUserExsist(String email, String password){
            userService=new UserServiceImpl();
            User user=userService.getUserInfo(email,password);
            if(user==null) return false;
            else{
                intent=new Intent(SplashActivity.this,MainActivity.class);
                intent.putExtra("user", user);
                return true;
            }
        }
    }
}
