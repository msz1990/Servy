package com.servy.servy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.servy.servy.R;
import com.servy.servy.domain.User;


/**
 * Created by shizhema on 16/5/16.
 */
public class SuccessActivity extends Activity {
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        init();
    }
    public void init(){
        Intent intent=getIntent();
        bundle=intent.getExtras();
    }

    public void backToMain(View view){
        User user= (User) bundle.get("user");
        Intent intent=new Intent(SuccessActivity.this,MainActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
        SuccessActivity.this.finish();
    }


}
