package com.systemtest;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.systemtest.model.OrderModel;
import com.systemtest.model.RegisterModule;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout addDBoy,orderDetail,deliveryBoyList;
    AlertDialog alertDialog;
    private DatabaseReference registerDBReference;
    private FirebaseDatabase mFirebaseInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDBoy =(LinearLayout)findViewById(R.id.linear1);
        orderDetail =(LinearLayout)findViewById(R.id.linear2);
        deliveryBoyList =(LinearLayout)findViewById(R.id.linear3);
        addDBoy.setOnClickListener(this);
        orderDetail.setOnClickListener(this);
        deliveryBoyList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.linear1:
                addForm();
                break;
            case R.id.linear2:
                Intent orderIntent = new Intent(MainActivity.this,OrderList.class);
                startActivity(orderIntent);

                break;
            case R.id.linear3:
                Intent intent = new Intent(MainActivity.this,DeliveryBoyList.class);
                startActivity(intent);
                finish();
                break;


        }
    }


    private void addForm() {
      //  LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        LayoutInflater factory = MainActivity.this.getLayoutInflater();
        final View addformView = factory.inflate(R.layout.addform_layout, null);

        final AlertDialog formDialog = new AlertDialog.Builder(this).create();
        formDialog.setView(addformView);
        addformView.findViewById(R.id.addsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                 EditText Id = (EditText)addformView.findViewById(R.id.addid);
                 EditText name = (EditText)addformView.findViewById(R.id.addname);
                 EditText mobile = (EditText)addformView.findViewById(R.id.addnumber);
                 EditText add = (EditText)addformView.findViewById(R.id.addaddress);
                 EditText dob = (EditText)addformView.findViewById(R.id.adddob);
                String idValue,nameValue,mobileValue,addValue,dobValue;

                idValue = Id.getText().toString();
                nameValue = name.getText().toString();
                mobileValue = mobile.getText().toString();
                addValue = add.getText().toString();
                dobValue = dob.getText().toString();



                mFirebaseInstance = FirebaseDatabase.getInstance();

                registerDBReference = FirebaseDatabase.getInstance().getReference("new_user");
                String userId = registerDBReference.push().getKey();
                RegisterModule registerModule = new RegisterModule(userId, idValue,nameValue,mobileValue,addValue,dobValue);
                registerDBReference.child(userId).setValue(registerModule);

                Toast.makeText(MainActivity.this, "Submitted successful", Toast.LENGTH_LONG).show();
                formDialog.dismiss();

            }
        });
        formDialog.show();

    }
}
