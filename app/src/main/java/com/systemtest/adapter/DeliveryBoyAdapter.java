package com.systemtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.systemtest.DeliveryBoyList;
import com.systemtest.R;
import com.systemtest.model.RegisterModule;

import java.util.ArrayList;
import java.util.List;

public class DeliveryBoyAdapter extends RecyclerView.Adapter<DeliveryBoyAdapter.DeliveryBoyViewHolder> {
    private Context context;
    List<RegisterModule> registerModules = new ArrayList<>();
    public DeliveryBoyAdapter(Context context, List<RegisterModule> registerModules) {
        this.context = context;
        this.registerModules =registerModules;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeliveryBoyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.deliveryboy_list_details, parent, false);
        DeliveryBoyAdapter.DeliveryBoyViewHolder deliveryBoyViewHolder = new DeliveryBoyAdapter.DeliveryBoyViewHolder(view);
        return deliveryBoyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryBoyViewHolder holder, int position) {
        System.out.println("NAme***********"+registerModules.get(position).getNameValue());
        holder.Name.setText(registerModules.get(position).getNameValue());

    }

    @Override
    public int getItemCount() {
        return registerModules.size();
    }

    public class DeliveryBoyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name;
        public DeliveryBoyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.name);
        }
    }
}
