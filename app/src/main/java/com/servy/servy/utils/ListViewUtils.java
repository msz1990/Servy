package com.servy.servy.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by shizhema on 16/5/24.
 */
public class ListViewUtils {
    public static <T> HashMap<String, Object> Object2HashMap(T t) throws IllegalAccessException {
        HashMap<String, Object> map=new HashMap<>();
        Class cls= t.getClass();
        Field[] fields=cls.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            String name=field.getName();
            map.put(name,field.get(t));
            //JSONObject obj=new JSONObject()
        }
        return map;
    }
}
