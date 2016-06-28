package com.servy.servy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.servy.servy.R;
import com.servy.servy.domain.BundleItem;
import com.servy.servy.service.OrderService;
import com.servy.servy.service.OrderServiceImpl;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by shizhema on 16/5/16.
 */
public class ReviewActivity extends Activity {
    private Bundle bundle;
    private TextView tv_item_price;
    private TextView tv_tip;
    private TextView tv_earned;
    private TextView tv_review_address;
    private TextView tv_arrive_time;
    private String panName;
    private LinearLayout ll_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        init();
    }

    public void init(){
        Intent intent=getIntent();
        bundle=intent.getExtras();
        panName=bundle.getString("panName");
        tv_item_price= (TextView) findViewById(R.id.tv_item_price);
        tv_tip= (TextView) findViewById(R.id.tv_tip);
        tv_earned= (TextView) findViewById(R.id.tv_earned);
        tv_review_address= (TextView) findViewById(R.id.tv_review_address);
        tv_arrive_time= (TextView) findViewById(R.id.tv_arrive_time);
        ll_items= (LinearLayout) findViewById(R.id.ll_items);
        if("deliverReview".equals(panName) || "myOrdersReview".equals(panName)){
            TextView tv_title= (TextView) findViewById(R.id.tv_title);
            tv_title.setText(R.string.order_detail);
            Map<String, Object> map= (Map<String, Object>) bundle.get("data");
            tv_item_price.setText((String) map.get("estimatedPrice"));
            tv_tip.setText((String) map.get("tip"));
            tv_earned.setText((String)map.get("orderTotal"));
            tv_review_address.setText((String)map.get("address"));
            tv_arrive_time.setText((String) map.get("deliverTime"));

            ArrayList<BundleItem> items= (ArrayList<BundleItem>) map.get("orderItems");
            LayoutInflater inflater = getLayoutInflater();
            for(BundleItem item:items){
                View itemLayout = inflater.inflate(R.layout.item_order_item, null);
                TextView tv_delete= (TextView) itemLayout.findViewById(R.id.tv_delete);
                tv_delete.setVisibility(View.GONE);
                EditText et_item_name= (EditText) itemLayout.findViewById(R.id.et_item_name);
                et_item_name.setVisibility(View.GONE);
                TextView tv_item_name= (TextView) itemLayout.findViewById(R.id.tv_item_name);
                tv_item_name.setVisibility(View.VISIBLE);
                tv_item_name.setText("●" + item.getText());
                ll_items.addView(itemLayout);
            }
        }else{
            tv_item_price.setText(intent.getStringExtra("orderTotal"));
            tv_tip.setText(intent.getStringExtra("tip"));
            tv_earned.setText(intent.getStringExtra("estimateTotal"));
            tv_review_address.setText(intent.getStringExtra("address"));
            tv_arrive_time.setText(intent.getStringExtra("deliverTime"));

            ArrayList<String> items=intent.getStringArrayListExtra("orderItems");
            LayoutInflater inflater = getLayoutInflater();
            for(String item:items){
                View itemLayout = inflater.inflate(R.layout.item_order_item, null);
                TextView tv_delete= (TextView) itemLayout.findViewById(R.id.tv_delete);
                tv_delete.setVisibility(View.GONE);
                EditText et_item_name= (EditText) itemLayout.findViewById(R.id.et_item_name);
                et_item_name.setVisibility(View.GONE);
                TextView tv_item_name= (TextView) itemLayout.findViewById(R.id.tv_item_name);
                tv_item_name.setVisibility(View.VISIBLE);
                tv_item_name.setText("●" + item);
                ll_items.addView(itemLayout);
            }
        }



    }

    public void deliverOrder(View view){
        Intent intent=new Intent(ReviewActivity.this,SuccessActivity.class);
        startActivity(intent);
        ReviewActivity.this.finish();
    }
    public void messageOrder(View view){

    }
    public void flagOrder(View view){

    }
    public void cancelOrder(View view){

    }
    public void deleteOrder(View view){

    }
    public void postOrder(View view){
        PostOrder postOrder=new PostOrder();
        postOrder.execute("100");
    }

    private class PostOrder extends AsyncTask<Object, Integer, Void>{

        @Override
        protected Void doInBackground(Object... params) {
            OrderService service=new OrderServiceImpl();
            String itemBundleId=(String)params[0];
            service.postOrder(itemBundleId,bundle);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent=new Intent(ReviewActivity.this,SuccessActivity.class);
            startActivity(intent);
            ReviewActivity.this.finish();
        }
    }
}
