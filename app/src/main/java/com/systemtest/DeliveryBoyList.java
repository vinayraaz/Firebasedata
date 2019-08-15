package com.systemtest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.systemtest.adapter.DeliveryBoyAdapter;
import com.systemtest.model.DeliveryBoyListModel;
import com.systemtest.model.RegisterModule;

import java.util.ArrayList;
import java.util.List;

public class DeliveryBoyList extends AppCompatActivity{
    DatabaseReference databaseArtists;
    List<RegisterModule> registerModules = new ArrayList<>();
    List<DeliveryBoyListModel> deliveryBoyListModels = new ArrayList<>();
    RecyclerView recyclerView;
    DeliveryBoyAdapter deliveryBoyAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliveryboy_listlayout);
        databaseArtists = FirebaseDatabase.getInstance().getReference("new_user");
//        datadisplay();
    }


    @Override
    protected void onStart() {
        super.onStart();

        final ProgressDialog pd = new ProgressDialog(DeliveryBoyList.this);
        pd.setMessage("Loading...");
        pd.show();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                registerModules.clear();
                deliveryBoyListModels.clear();
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    RegisterModule note = noteSnapshot.getValue(RegisterModule.class);
                    registerModules.add(note);
                }
               /* for (int i=0;i<registerModules.size();i++){
                    if (!ConstantClass.CURRENTUSER_NAME.equals(registerModules.get(i).getName())){
                        friendLists.add(new FriendList(registerModules.get(i).getUserId(),registerModules.get(i).getName(),registerModules.get(i).getEmail(),
                                registerModules.get(i).getPassword(),registerModules.get(i).getMobile(),registerModules.get(i).getGender()));


                    }else {
                        registerModules.remove(i);
                    }

                }*/
               // System.out.println("message********friendLists"+friendLists.size());
                System.out.println("message********registerModules"+registerModules.size());
                ContactListData(registerModules);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.dismiss();
                Log.d("ERROR", databaseError.getMessage());
            }
        });
    }

    private void ContactListData(List<RegisterModule> registerModules) {
        recyclerView = (RecyclerView)findViewById(R.id.contact_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        deliveryBoyAdapter = new DeliveryBoyAdapter (this,registerModules);
        recyclerView.setAdapter(deliveryBoyAdapter);
    }
}
