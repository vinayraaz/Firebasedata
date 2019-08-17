package com.systemtest.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.systemtest.DeliveryBoyList;
import com.systemtest.MainActivity;
import com.systemtest.OrderList;
import com.systemtest.R;
import com.systemtest.model.DeliveryBoyListModel;
import com.systemtest.model.OrderModel;
import com.systemtest.model.OrderModule;
import com.systemtest.model.RegisterModule;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    List<OrderModel> orderModels2 = new ArrayList<>();
    ArrayList<String> orderModels1 = new ArrayList<>();

    List<OrderModule> orderModels = new ArrayList<>();
    List<DeliveryBoyListModel> deliveryBoyListModels = new ArrayList<>();

    DatabaseReference databaseArtists, updatabase, updateOrder;
    private Context context;
    String OName, OUserId;
    String orderposition;
    ListView simpleList;
    ProgressDialog pd;

    public OrderListAdapter(Context context, List<OrderModule> orderModels) {
        this.context = context;
        this.orderModels = orderModels;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public OrderListAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_details, parent, false);
        OrderListAdapter.OrderViewHolder orderViewHolder = new OrderListAdapter.OrderViewHolder(view);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.OrderViewHolder holder, final int position) {
        holder.orderId.setText("Order Details : "+ orderModels.get(position).getOrderDetails());
        holder.orderName.setText("Order Name : "+ orderModels.get(position).getOrderName());
        holder.orderAdd.setText("Order Address : " +orderModels.get(position).getOrderAdd());
        int orderstatus = orderModels.get(position).getOrderStatus();

        if (orderstatus == 0) {
            holder.llButton.setVisibility(View.VISIBLE);
            holder.OrderValue.setText("Pending");
        } else {
            holder.OrderValue.setText("Allotted");
            holder.llButton.setVisibility(View.GONE);
        }

        holder.llButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderModels1.clear();
                pd = new ProgressDialog(context);
                pd.setMessage("Loading...");
                pd.show();
                orderposition = orderModels.get(position).getOrderId();
                DBoyLoad();

           }
        });

    }

    private void DBoyLoad() {

        databaseArtists = FirebaseDatabase.getInstance().getReference("new_user");
        databaseArtists.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pd.dismiss();
                deliveryBoyListModels.clear();

                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    DeliveryBoyListModel note = noteSnapshot.getValue(DeliveryBoyListModel.class);
                    deliveryBoyListModels.add(note);
                }

                for (int j = 0; j < deliveryBoyListModels.size(); j++) {
                    int dBoyStatus = deliveryBoyListModels.get(j).getDeliveryBoyStatus();
                    if (dBoyStatus == 0) {
                        OName = deliveryBoyListModels.get(j).getNameValue();
                        orderModels1.add(OName);
                    }
                }

                LayoutInflater factory = LayoutInflater.from(context);
                final View addformView = factory.inflate(R.layout.order_layout, null);
                final AlertDialog formDialog = new AlertDialog.Builder(context).create();
                formDialog.setView(addformView);
                try{
                    formDialog.show();
                    simpleList = (ListView) addformView.findViewById(R.id.simpleListView);

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.list_view_items, R.id.textView, orderModels1);
                    simpleList.setAdapter(arrayAdapter);

                    simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String name = parent.getItemAtPosition(position).toString();
                            for(int i=0;i<deliveryBoyListModels.size();i++){
                                if (name.equals(deliveryBoyListModels.get(i).getNameValue())){
                                    OUserId = deliveryBoyListModels.get(i).getUserId().toString();
                                    updateData();
                                }
                            }
                            formDialog.dismiss();

                        }
                    });
                }catch (WindowManager.BadTokenException e){
                    Log.i("ERROR******" ,e.getMessage());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("ERRORFIREBase",databaseError.toString());

            }
        });
    }

    private void updateData() {
        updatabase = FirebaseDatabase.getInstance().getReference("new_user");
        updatabase.child(OUserId).child("deliveryBoyStatus").setValue(1);
        Toast.makeText(context, "Delivery Boy Allotted Success", Toast.LENGTH_SHORT).show();

        OrderStatus();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void OrderStatus() {
        updateOrder = FirebaseDatabase.getInstance().getReference("order_data");
        updateOrder.child(orderposition).child("orderStatus").setValue(1);

    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView orderId, orderName, orderAdd,OrderValue;
        public Button llButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = (TextView) itemView.findViewById(R.id.orderid);
            OrderValue = (TextView) itemView.findViewById(R.id.textvalue);
            orderName = (TextView) itemView.findViewById(R.id.ordername);
            orderAdd = (TextView) itemView.findViewById(R.id.orderadd);
            llButton = (Button) itemView.findViewById(R.id.orderaccept);
            llButton.setOnClickListener(this);
            // itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
