package com.servy.servy.utils;


import com.servy.servy.domain.BundleItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shizhema on 16/5/20.
 */
public class JSONUtils {

    public static <T> T json2Object(JSONObject jsonObject,Class<T> cls) throws IllegalAccessException, InstantiationException, JSONException, ClassNotFoundException {
        T obj=cls.newInstance();
        //Class<?> obj=Class.forName(cls.getName());
        Field[] fields=cls.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            String type=field.getType().getSimpleName();
            if(field.getName().equalsIgnoreCase("orderItems")){
                JSONArray array=jsonObject.getJSONArray("orderitems");
                List<BundleItem> list=new ArrayList<>();
                for(int i=0;i<array.length();i++){
                    BundleItem item= json2Object(array.getJSONObject(i), BundleItem.class);
                    list.add(item);
                }
                field.set(obj,list);
                continue;
            }
            if("String".equalsIgnoreCase(type)){
                field.set(obj, jsonObject.getString(field.getName().toLowerCase()));
            }else if("int".equalsIgnoreCase(type) || "Integer".equalsIgnoreCase(type)){
                field.set(obj, jsonObject.getInt(field.getName()));
            }else if("boolean".equalsIgnoreCase(type)){
                field.set(obj, jsonObject.getBoolean(field.getName()));
            }else if("Long".equalsIgnoreCase(type)){
                field.set(obj, jsonObject.getLong(field.getName()));
            }else if("Double".equalsIgnoreCase(type)){
                field.set(obj, jsonObject.getDouble(field.getName()));
            }else if("Object".equalsIgnoreCase(type)){
                field.set(obj, jsonObject.get(field.getName()));
            }else if("JSONArray".equalsIgnoreCase(type)){
                field.set(obj, jsonObject.getJSONArray(field.getName()));
            }else if("JSONObject".equalsIgnoreCase(type)){
                field.set(obj, jsonObject.getJSONObject(field.getName()));
            }

        }
        return obj;
    }
}
