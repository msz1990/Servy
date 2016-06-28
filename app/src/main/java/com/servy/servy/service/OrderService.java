package com.servy.servy.service;

import android.os.Bundle;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shizhema on 16/5/23.
 */
public interface OrderService {
    void postOrder(String itemBundleId, Bundle bundle);
    List<HashMap<String, Object>> getDeliverOrders(String userId);
    List<HashMap<String, Object>> getOrders(String userId);
}
