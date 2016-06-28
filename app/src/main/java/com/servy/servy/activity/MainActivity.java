package com.servy.servy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.servy.servy.R;
import com.servy.servy.domain.User;


/**
 * Created by shizhema on 16/5/5.
 */
public class MainActivity extends Activity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        Intent intent=getIntent();
        user=(User)intent.getExtras().get("user");
        TextView tv_first_name= (TextView) findViewById(R.id.tv_first_name);
        TextView tv_last_name= (TextView) findViewById(R.id.tv_last_name);
        tv_first_name.setText(user.getUsername());
    }

    public void placeOrder(View view){
        Intent intent=new Intent(MainActivity.this,ListActivity.class);
        intent.putExtra("panName","placeOrder");
        intent.putExtra("user",user);
        startActivity(intent);
    }
    public void deliverItems(View view){
        Intent intent=new Intent(MainActivity.this,ListActivity.class);
        intent.putExtra("panName","deliverOrder");
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void myOrders(View view){
        Intent intent=new Intent(MainActivity.this,ListActivity.class);
        intent.putExtra("panName","myOrders");
        intent.putExtra("user",user);
        startActivity(intent);
    }
}
