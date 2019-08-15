package com.systemtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.systemtest.adapter.DeliveryBoyAdapter;
import com.systemtest.adapter.OrderListAdapter;
import com.systemtest.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderList extends AppCompatActivity {
    List<OrderModel> orderModels = new ArrayList<>();
    RecyclerView recyclerView;
    OrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        orderList();
    }

    private void orderList() {
      //  for (int i = 0; i <= 5; i++) {

            orderModels.add(new OrderModel("1", "ABC", "Bangalore", 0));
            orderModels.add(new OrderModel("2", "Ram", "Bangalore", 1));
            orderModels.add(new OrderModel("3", "XYZ", "Bangalore", 1));
            orderModels.add(new OrderModel("4", "PQR", "Bangalore", 0));

      //  }
        orderDetailsLoad(orderModels);
    }

    private void orderDetailsLoad(List<OrderModel> orderModels) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        orderListAdapter = new OrderListAdapter(this, orderModels);
        recyclerView.setAdapter(orderListAdapter);
    }
}
