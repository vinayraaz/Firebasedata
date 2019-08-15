package com.systemtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.systemtest.OrderList;
import com.systemtest.R;
import com.systemtest.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>{
    List<OrderModel> orderModels = new ArrayList<>();
    private Context context;
    public OrderListAdapter(Context context, List<OrderModel> orderModels) {
        this.context =context;
        this.orderModels =orderModels;
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
    public void onBindViewHolder(@NonNull OrderListAdapter.OrderViewHolder holder, int position) {
        holder.orderId.setText(orderModels.get(position).getOrderId());
        holder.orderName.setText(orderModels.get(position).getOrderName());
        holder.orderAdd.setText(orderModels.get(position).getOrderAdd());
        int orderstatus = orderModels.get(position).getOrderStatus();
        if (orderstatus== 0){
            holder.llButton.setVisibility(View.VISIBLE);
        }else {
            holder.llButton.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView orderId, orderName,orderAdd;
        public LinearLayout llButton;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = (TextView)itemView.findViewById(R.id.orderid);
            orderName = (TextView)itemView.findViewById(R.id.ordername);
            orderAdd = (TextView)itemView.findViewById(R.id.orderadd);
            llButton = (LinearLayout)itemView.findViewById(R.id.orderaccept);
        }
    }
}
