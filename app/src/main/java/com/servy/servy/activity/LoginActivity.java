package com.servy.servy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.servy.servy.R;
import com.servy.servy.domain.User;
import com.servy.servy.service.UserService;
import com.servy.servy.service.UserServiceImpl;


public class LoginActivity extends Activity {

    private EditText et_email;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init(){
        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
    }

    public void login(View view){
        String email=et_email.getText().toString().trim();
        String password=et_password.getText().toString().trim();
            if("".equals(email) || null==email || "".equals(password) || null==password){
                Toast.makeText(getApplicationContext(),"Email or password can't be empty ",Toast.LENGTH_LONG).show();
                return;
            }

        UserExsist userExsist =new UserExsist();
        userExsist.execute(email, password);
    }



    private class UserExsist extends AsyncTask<String, Integer, Boolean>{

        private Intent intent;
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
                SharedPreferences sp=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("email",email);
                editor.putString("password", password);
                editor.commit();
                startActivity(intent);
                LoginActivity.this.finish();
            }else{
                Toast.makeText(getApplicationContext(),"Email or password is incorrect.",Toast.LENGTH_LONG).show();
            }

        }

        private UserService userService;
        public boolean isUserExsist(String email, String password){
            userService=new UserServiceImpl();
            User user=userService.getUserInfo(email,password);
            if(user==null) return false;
            else{
                intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("user",user);
                return true;
            }
        }
    }
}
