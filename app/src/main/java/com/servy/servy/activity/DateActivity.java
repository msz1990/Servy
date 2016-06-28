package com.servy.servy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.servy.servy.R;


/**
 * Created by shizhema on 16/5/11.
 */
public class DateActivity extends Activity{
    private DatePicker dp_date;
    private TimePicker tp_time;
    private int year;
    private String month;
    private String day;
    private int hour;
    private int min;
    private final String[] MONTHSTR={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    private String deliverTime;
    //private HashMap<String,String> orderData;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        init();
    }

    public void init(){
        dp_date=(DatePicker)findViewById(R.id.dp_date);
        tp_time=(TimePicker)findViewById(R.id.tp_time);
        deliverTime="";
        Intent intent=getIntent();
        bundle=intent.getExtras();
        //orderData=(HashMap<String,String>)bundle.get("orderData");
    }

    public void confirm(View view){
        if(dp_date.getVisibility()==View.VISIBLE){
            year=dp_date.getYear();
            month=MONTHSTR[dp_date.getMonth()-1];
            day=dp_date.getDayOfMonth()+"";
            deliverTime=month+" "+day+" ";
            dp_date.setVisibility(View.GONE);
            tp_time.setVisibility(View.VISIBLE);
            //Toast.makeText(this,deliverTime,Toast.LENGTH_LONG).show();
        }else{
            hour=tp_time.getCurrentHour();
            min=tp_time.getCurrentMinute();
            String minStr=min+"";
            if(min<10){
                minStr="0"+minStr;
            }
            if(hour>=12){
                if(hour>12) hour-=12;
                deliverTime+=hour+":"+minStr+" PM";
            }else{
                deliverTime+=hour+":"+minStr+" AM";
            }
            //Toast.makeText(this,deliverTime,Toast.LENGTH_LONG).show();
            Intent intent =new Intent(DateActivity.this,ListActivity.class);
            //orderData.put("deliverTime",deliverTime);
            intent.putExtras(bundle);
            intent.putExtra("deliverTime", deliverTime);
            intent.putExtra("panName","newOrderSecond");
            startActivity(intent);
        }
    }
}
