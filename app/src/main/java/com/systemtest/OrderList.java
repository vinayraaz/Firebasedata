package com.systemtest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.systemtest.adapter.DeliveryBoyAdapter;
import com.systemtest.adapter.OrderListAdapter;
import com.systemtest.model.DeliveryBoyListModel;
import com.systemtest.model.OrderModel;
import com.systemtest.model.OrderModule;
import com.systemtest.model.RegisterModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderList extends AppCompatActivity {
    List<OrderModel> orderModels1 = new ArrayList<>();
    List<OrderModule> orderModels = new ArrayList<>();
    RecyclerView recyclerView;
    OrderListAdapter orderListAdapter;
    private DatabaseReference registerDBReference, orderDataLoad, updateOrder, updateDBoy;
    private FirebaseDatabase mFirebaseInstance;
    DatabaseReference databaseArtists;
    List<DeliveryBoyListModel> deliveryBoyListModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        orderDataLoad = FirebaseDatabase.getInstance().getReference("order_data");
        loadOrderData();
         deliveryBoyList();

    }

    private void deliveryBoyList() {
        updateOrder = FirebaseDatabase.getInstance().getReference("new_user");
        updateOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    DeliveryBoyListModel note = noteSnapshot.getValue(DeliveryBoyListModel.class);
                    deliveryBoyListModels.add(note);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
       // loadOrderData();
    }

    private void loadOrderData() {
        final ProgressDialog pd = new ProgressDialog(OrderList.this);
        pd.setMessage("Loading...");
        pd.show();
        orderDataLoad.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pd.dismiss();
                orderModels.clear();
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    OrderModule orderModule = noteSnapshot.getValue(OrderModule.class);
                    orderModels.add(orderModule);
                }
                if (orderModels.size()==0){
                    Toast.makeText(OrderList.this,"No Order ",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrderList.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else {
                    orderDetailsLoad(orderModels);
                    ServiceCall();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void orderDetailsLoad(List<OrderModule> orderModels) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        orderListAdapter = new OrderListAdapter(this, orderModels);
        recyclerView.setAdapter(orderListAdapter);
    }

    private void ServiceCall() {

        int delay = 5*60000; // delay for 0 sec.
        int period = 9*60000; // repeat every 10 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                for (int j = 0; j < deliveryBoyListModels.size(); j++) {
                    int dBoyStatus = deliveryBoyListModels.get(j).getDeliveryBoyStatus();
                    if (dBoyStatus == 1) {
                        String OName = deliveryBoyListModels.get(j).getNameValue();
                        String OUserId = deliveryBoyListModels.get(j).getUserId();

                        updateDBoy = FirebaseDatabase.getInstance().getReference("new_user");
                        updateDBoy.child(OUserId).child("deliveryBoyStatus").setValue(0);
                    } else {

                    }
                }
            }
        }, delay, period);

    }

}
