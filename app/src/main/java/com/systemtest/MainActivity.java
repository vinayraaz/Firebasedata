package com.systemtest;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.systemtest.model.OrderModel;
import com.systemtest.model.OrderModule;
import com.systemtest.model.RegisterModule;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout orderDetail, deliveryBoyList;
    AlertDialog alertDialog;
    private DatabaseReference registerDBReference, orderDBReference;
    private FirebaseDatabase mFirebaseInstance;
    private int deliveryBoyStatus = 0, orderStatus = 0;
    TextView addDBoy, addOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDBoy = (TextView) findViewById(R.id.textview1);
        addOrder = (TextView) findViewById(R.id.textview2);
        orderDetail = (LinearLayout) findViewById(R.id.linear2);
        deliveryBoyList = (LinearLayout) findViewById(R.id.linear3);
        addDBoy.setOnClickListener(this);
        addOrder.setOnClickListener(this);
        orderDetail.setOnClickListener(this);
        deliveryBoyList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.textview1:
                addForm();
                break;
            case R.id.textview2:
                addOrder();
                break;
            case R.id.linear2:
                Intent orderIntent = new Intent(MainActivity.this, OrderList.class);
                orderIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(orderIntent);

                break;
            case R.id.linear3:
                Intent intent = new Intent(MainActivity.this, DeliveryBoyList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;


        }
    }

    private void addOrder() {
        LayoutInflater factory = MainActivity.this.getLayoutInflater();
        final View addformView = factory.inflate(R.layout.addorder_layout, null);

        final AlertDialog formDialog = new AlertDialog.Builder(this).create();
        formDialog.setView(addformView);
        try {
            formDialog.show();
            addformView.findViewById(R.id.addsubmit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText name = (EditText) addformView.findViewById(R.id.addname);
                    EditText order = (EditText) addformView.findViewById(R.id.addorder);
                    EditText address = (EditText) addformView.findViewById(R.id.addaddress);
                    String orderName, orderDetails, orderAdd;
                    orderName = name.getText().toString();
                    orderDetails = order.getText().toString();
                    orderAdd = address.getText().toString();
                    if (name.getText().toString().isEmpty() || order.getText().toString().isEmpty() || address.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please fill all details", Toast.LENGTH_LONG).show();
                    } else {
                        mFirebaseInstance = FirebaseDatabase.getInstance();
                        orderDBReference = FirebaseDatabase.getInstance().getReference("order_data");
                        String orderId = orderDBReference.push().getKey();
                        OrderModule orderModule = new OrderModule(orderId, orderName, orderDetails, orderAdd, orderStatus);
                        orderDBReference.child(orderId).setValue(orderModule);

                        Toast.makeText(MainActivity.this, "Order Submit successful", Toast.LENGTH_LONG).show();
                        formDialog.dismiss();
                    }


                }
            });
        } catch (WindowManager.BadTokenException e) {

        }


    }


    private void addForm() {
        LayoutInflater factory = MainActivity.this.getLayoutInflater();
        final View addformView = factory.inflate(R.layout.addform_layout, null);

        final AlertDialog formDialog = new AlertDialog.Builder(this).create();
        formDialog.setView(addformView);
        try{
            formDialog.show();
            addformView.findViewById(R.id.addsubmit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //your business logic
                    EditText Id = (EditText) addformView.findViewById(R.id.addid);
                    EditText name = (EditText) addformView.findViewById(R.id.addname);
                    EditText mobile = (EditText) addformView.findViewById(R.id.addnumber);
                    EditText add = (EditText) addformView.findViewById(R.id.addaddress);
                    EditText dob = (EditText) addformView.findViewById(R.id.adddob);
                    String idValue, nameValue, mobileValue, addValue, dobValue;

                    idValue = Id.getText().toString();
                    nameValue = name.getText().toString();
                    mobileValue = mobile.getText().toString();
                    addValue = add.getText().toString();
                    dobValue = dob.getText().toString();


                    if (Id.getText().toString().isEmpty() || name.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() ||
                            add.getText().toString().isEmpty() || dob.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please fill all details", Toast.LENGTH_LONG).show();

                    } else {
                        mFirebaseInstance = FirebaseDatabase.getInstance();
                        registerDBReference = FirebaseDatabase.getInstance().getReference("new_user");
                        String userId = registerDBReference.push().getKey();
                        RegisterModule registerModule = new RegisterModule(userId, idValue, nameValue, mobileValue, addValue, dobValue, deliveryBoyStatus);
                        registerDBReference.child(userId).setValue(registerModule);

                        Toast.makeText(MainActivity.this, "Submitted successful", Toast.LENGTH_LONG).show();
                        formDialog.dismiss();
                    }

                }
            });
        }catch (WindowManager.BadTokenException e){

        }

    }
}
