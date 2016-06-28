package com.servy.servy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.servy.servy.R;
import com.servy.servy.domain.User;
import com.servy.servy.service.OrderService;
import com.servy.servy.service.OrderServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shizhema on 16/5/7.
 */
public class ListActivity extends Activity implements OnMapReadyCallback {
    private ListView lv_content;
    private LinearLayout ll_head;
    private TextView tv_title;
    private LinearLayout ll_deliver_bar;
    private LinearLayout ll_main;
    private LinearLayout ll_map;
    private LinearLayout ll_order_item;
    private TextView tv_deliver_num;
    private String curPanName;
    private SimpleAdapter adapter;
    private TextView tv_next;
    private List<HashMap<String, Object>> data;
    private String category;
    private LayoutInflater inflater;
    private TextView tv_estimate_total;
    private EditText et_tip_amount;
    private EditText et_order_total;
    private TextView tv_deliver_time;
    private EditText et_order_title;
    private EditText et_address;
    private EditText et_zip_code;
    private EditText et_phone_number;
    private ImageButton ib_next;
    private Bundle bundle;
    private OrderService orderService;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    public void init() {
        lv_content = (ListView) findViewById(R.id.lv_content);
        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_deliver_bar = (LinearLayout) findViewById(R.id.ll_deliver_bar);
        tv_deliver_num = (TextView) findViewById(R.id.tv_deliver_num);
        tv_next = (TextView) findViewById(R.id.tv_next);
        ib_next = (ImageButton) findViewById(R.id.ib_next);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        curPanName = bundle.getString("panName");

        if ("placeOrder".equals(curPanName)) {
            ll_deliver_bar.setVisibility(View.GONE);
            lv_content.setVisibility(View.VISIBLE);
            HashMap<String, Object> map = new HashMap<>();
            map.put("iv_background", R.drawable.fastfood);
            map.put("tv_category", R.string.fast_food);
            data = new ArrayList<>();
            data.add(map);
            adapter = new SimpleAdapter(ListActivity.this, data, R.layout.item_place_order,
                    new String[]{"iv_background"},
                    new int[]{R.id.iv_background}
            );
            lv_content.setAdapter(adapter);
        } else if ("newOrderFirst".equals(curPanName)) {
            tv_title.setText(R.string.new_order);
            //orderData=(HashMap<String,String>)bundle.get("orderData");
            category = bundle.getString("category");
            ll_deliver_bar.setVisibility(View.GONE);
            lv_content.setVisibility(View.GONE);
            tv_next.setVisibility(View.VISIBLE);


            inflater = getLayoutInflater();
            View bodyLayout = inflater.inflate(R.layout.item_new_order_first, null);
            ll_main.addView(bodyLayout);

            ll_order_item = (LinearLayout) bodyLayout.findViewById(R.id.ll_order_item);
            et_order_title= (EditText) findViewById(R.id.et_order_title);

            if (bundle.containsKey("orderTitle")) {

                et_order_title.setText(bundle.getString("orderTitle"));
            }
            if(bundle.containsKey("orderItems")){
                ArrayList<String> list=bundle.getStringArrayList("orderItems");
                for(String str:list){
                    View itemView = inflater.inflate(R.layout.item_order_item, null);
                    ll_order_item.addView(itemView);
                    LinearLayout itemLayout=(LinearLayout)itemView;
                    TextView textView=(TextView)itemLayout.getChildAt(0);
                    textView.setText(str);
                }
            }
        } else if ("newOrderSecond".equals(curPanName)) {
            tv_title.setText(R.string.new_order);
            ll_deliver_bar.setVisibility(View.GONE);
            lv_content.setVisibility(View.GONE);
            tv_next.setVisibility(View.VISIBLE);

            inflater = getLayoutInflater();
            View bodyLayout = inflater.inflate(R.layout.item_new_order_second, null);
            ll_main = (LinearLayout) findViewById(R.id.ll_main);
            ll_main.addView(bodyLayout);

//            View mapLayout=inflater.inflate(R.layout.activity_maps,null);
//            ll_map= (LinearLayout) findViewById(R.id.ll_map);
//            ll_map.addView(mapLayout);

            tv_estimate_total = (TextView) findViewById(R.id.tv_estimate_total);
            et_tip_amount = (EditText) findViewById(R.id.et_tip_amount);
            et_order_total = (EditText) findViewById(R.id.et_order_total);
            tv_deliver_time = (TextView) findViewById(R.id.tv_deliver_time);

            TextWatcher textWatcher=new TextWatcher() {
                int tip=0;
                int total=0;
                int estimateTotal=0;
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!"".equals(et_tip_amount.getText().toString())){
                        tip=Integer.parseInt(et_tip_amount.getText().toString());
                    }
                    if(!"".equals(et_order_total.getText().toString())){
                        total=Integer.parseInt(et_order_total.getText().toString());
                    }
                    estimateTotal=tip+total;
                    tv_estimate_total.setText(estimateTotal+"");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };

            et_order_total.addTextChangedListener(textWatcher);
            et_tip_amount.addTextChangedListener(textWatcher);


            if (bundle.containsKey("estimateTotal")) {

                tv_estimate_total.setText(bundle.getString("estimateTotal"));
            }
            if (bundle.containsKey("tip")) {

                et_tip_amount.setText(bundle.getString("tip"));
            }
            if (bundle.containsKey("orderTotal")) {

                et_order_total.setText(bundle.getString("orderTotal"));
            }
            if (bundle.containsKey("deliverTime")) {

                tv_deliver_time.setText(bundle.getString("deliverTime"));
            }

        } else if ("newOrderThird".equals(curPanName)) {
            tv_title.setText(R.string.new_order);
            ll_deliver_bar.setVisibility(View.GONE);
            lv_content.setVisibility(View.GONE);
            tv_next.setVisibility(View.VISIBLE);
            tv_next.setText(R.string.review);

            inflater = getLayoutInflater();
            View bodyLayout = inflater.inflate(R.layout.item_new_order_third, null);
            ll_main = (LinearLayout) findViewById(R.id.ll_main);
            ll_main.addView(bodyLayout);

            et_address= (EditText) findViewById(R.id.et_address);
            et_phone_number= (EditText) findViewById(R.id.et_phone_number);
            et_zip_code= (EditText) findViewById(R.id.et_zip_code);

            if(bundle.containsKey("address")){
                et_address.setText(bundle.getString("address"));
            }
            if(bundle.containsKey("zip")){
                et_zip_code.setText(bundle.getString("zip"));
            }
            if(bundle.containsKey("phoneNumber")){
                et_phone_number.setText(bundle.getString("phoneNumber"));
            }


        }else if("deliverOrder".equals(curPanName)){
            tv_title.setText(R.string.deliver_orders);
            ll_deliver_bar.setVisibility(View.GONE);
            lv_content.setVisibility(View.VISIBLE);
            ll_main.setVisibility(View.GONE);
            tv_next.setVisibility(View.INVISIBLE);
            //ib_next.setVisibility(View.VISIBLE);
            //ib_next.setImageResource(R.drawable.ico_delivery);
            ShowDeliverOrders showDeliverOrders=new ShowDeliverOrders();
            showDeliverOrders.execute();
        }else if("myOrders".equals(curPanName)){
            tv_title.setText(R.string.my_order);
            ll_deliver_bar.setVisibility(View.GONE);
            lv_content.setVisibility(View.VISIBLE);
            ll_main.setVisibility(View.GONE);
            tv_next.setVisibility(View.GONE);
            ib_next.setVisibility(View.VISIBLE);
            ib_next.setImageResource(R.drawable.ico_clock);
            ShowOrders showOrders=new ShowOrders();
            showOrders.execute();
        }

    }

    public void placeOrder(View view) {
        Intent intent = new Intent(ListActivity.this, ListActivity.class);
        intent.putExtras(bundle);
        intent.putExtra("panName", "newOrderFirst");
        //orderData=new HashMap<>();
        //orderData.put("category",category);
        category="fastFood";
        intent.putExtra("category", category);
        startActivity(intent);
        //ListActivity.this.finish();
    }

    public void next(View view) {
        if ("newOrderFirst".equals(curPanName)) {
            ArrayList<String> itemList = new ArrayList<>();
            for (int i = 0; i < ll_order_item.getChildCount(); i++) {
                LinearLayout layout = (LinearLayout) ll_order_item.getChildAt(i);
                TextView textView = (TextView) layout.getChildAt(0);
                if (!"".equals(textView.getText().toString()))
                    itemList.add(textView.getText().toString());
            }
            String orderTitle=et_order_title.getText().toString();
            if("".equals(orderTitle) || null==orderTitle){
                Toast.makeText(getApplicationContext(), R.string.order_title_warning, Toast.LENGTH_LONG).show();
                return;
            }
            if(itemList.isEmpty()){
                Toast.makeText(getApplicationContext(), R.string.order_item_warning, Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(ListActivity.this, ListActivity.class);
            intent.putExtras(bundle);
            intent.putExtra("panName", "newOrderSecond");
            intent.putStringArrayListExtra("orderItems", itemList);
            intent.putExtra("orderTitle",orderTitle);
            //intent.putExtra("user",user);
            startActivity(intent);
        } else if ("newOrderSecond".equals(curPanName)) {
            String tip=et_tip_amount.getText().toString();
            String orderTotal=et_order_total.getText().toString();
            if("".equals(tip) || "".equals(orderTotal)){
                Toast.makeText(getApplicationContext(), R.string.empty_warning, Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(ListActivity.this, ListActivity.class);
            intent.putExtras(bundle);
            intent.putExtra("panName", "newOrderThird");
            intent.putExtra("tip",tip );
            intent.putExtra("orderTotal", orderTotal);
            intent.putExtra("estimateTotal",tv_estimate_total.getText().toString());
            startActivity(intent);
        } else if ("newOrderThird".equals(curPanName)) {
            String address=et_address.getText().toString();
            String zip=et_zip_code.getText().toString();
            String phoneNumber=et_phone_number.getText().toString();
            String lat="0";
            String longtitude="0";
            String city="city";
            String state="state";
            if("".equals(address) || "".equals(zip) || "".equals(phoneNumber) || "".equals(lat) || "".equals(longtitude)
                    || "".equals(city) || "".equals(state)){
                Toast.makeText(getApplicationContext(), R.string.empty_warning, Toast.LENGTH_LONG).show();
                return;
            }
            if(!bundle.containsKey("deliverTime")){
                Toast.makeText(getApplicationContext(), R.string.deliver_time_warning, Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(ListActivity.this, ReviewActivity.class);
            intent.putExtras(bundle);
            intent.putExtra("panName","postOrderReview");
            intent.putExtra("address", address);
            intent.putExtra("zip", zip);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("lat", lat);
            intent.putExtra("long", longtitude);
            intent.putExtra("city", city);
            intent.putExtra("state",state);
            startActivity(intent);
        }
    }

    public void selectDate(View view) {
        Intent intent = new Intent(ListActivity.this, DateActivity.class);
        intent.putExtras(bundle);
        intent.putExtra("panName", "newOrderThird");
        intent.putExtra("tip", et_tip_amount.getText().toString());
        intent.putExtra("orderTotal", et_order_total.getText().toString());
        intent.putExtra("estimateTotal", tv_estimate_total.getText().toString());
        startActivity(intent);
    }

    public void addItem(View view) {
        inflater = getLayoutInflater();
        //View bodyLayout=inflater.inflate(R.layout.item_new_order_first,null);
        View itemLayout = inflater.inflate(R.layout.item_order_item, null);
        //ll_order_item=(LinearLayout)bodyLayout.findViewById(R.id.ll_order_item);
        ll_order_item.addView(itemLayout);
    }

    public void deleteOrderItem(View view) {
        ll_order_item.removeView((View) view.getParent());
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private class ShowDeliverOrders extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... params) {
            orderService=new OrderServiceImpl();
            User user= (User) bundle.get("user");
            data = orderService.getDeliverOrders(user.getId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter = new SimpleAdapter(ListActivity.this, data, R.layout.item_order_list,
                    new String[]{"status","title","address","deliverBy","estimatedPrice","tip","orderTotal"},
                    new int[]{R.id.tv_order_status,R.id.tv_order_title_list,R.id.tv_order_address,
                            R.id.tv_order_time,R.id.tv_item_price,R.id.tv_tip,R.id.tv_earned}
            );
            lv_content.setAdapter(adapter);
            lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListActivity.this, ReviewActivity.class);
                    intent.putExtra("panName", "deliverReview");
                    HashMap<String, Object> map = data.get(position);
                    intent.putExtra("data", map);
                    startActivity(intent);
                }
            });
        }

    }

    private class ShowOrders extends AsyncTask<String, Integer, Void>{

        @Override
        protected Void doInBackground(String... params) {
            orderService=new OrderServiceImpl();
            User user= (User) bundle.get("user");
            data = orderService.getOrders(user.getId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter = new SimpleAdapter(ListActivity.this, data, R.layout.item_order_list,
                    new String[]{"status","title","address","deliverBy","estimatedPrice","tip","orderTotal"},
                    new int[]{R.id.tv_order_status,R.id.tv_order_title_list,R.id.tv_order_address,
                            R.id.tv_order_time,R.id.tv_item_price,R.id.tv_tip,R.id.tv_earned}
            );
            lv_content.setAdapter(adapter);
            lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListActivity.this, ReviewActivity.class);
                    intent.putExtra("panName", "myOrdersReview");
                    HashMap<String, Object> map = data.get(position);
                    intent.putExtra("data", map);
                    startActivity(intent);
                }
            });
        }
    }


}
