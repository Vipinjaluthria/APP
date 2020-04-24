package com.example.profileinformation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Delete extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String userid;
    FirebaseFirestore fstore;
    ArrayAdapter adapter;
    ProgressDialog progressDialog;
    TextView dbooking;
    ListView listView;
    AlertDialog.Builder alertdialog;
    Button delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.listview);
        delete = findViewById(R.id.Delete);
        dbooking=findViewById(R.id.nobookingtodelete);
        dbooking.setVisibility(View.GONE);


        progressDialog = new ProgressDialog(Delete.this);
        progressDialog.setTitle("Loading ......");
        progressDialog.show();
        userid = firebaseAuth.getCurrentUser().getUid();
        final List<String> data = new ArrayList<>();

        CollectionReference collectionReference = fstore.collection("Bookings");
           fstore.collection("Bookings").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        DocumentSnapshot documentSnapshot = task.getResult();
                        final String NAME = documentSnapshot.getString("NAME");
                        data.add("NAME -" + NAME);
                        data.add("CONTACT - " + documentSnapshot.getString("CONTACT"));
                        data.add("CAB NAME - " + documentSnapshot.getString("CARNAME"));
                        data.add("GENDER - " + documentSnapshot.getString("GENDER"));
                        data.add("CAR NUMBER - " + documentSnapshot.getString("CARNUMBER"));
                        data.add("ADDITIONAL LUGGAGE - " + documentSnapshot.getString("ADDITIONALLUGGAGE"));
                        data.add("DATE - " + documentSnapshot.getString("DATE"));
                        data.add("TIME - " + documentSnapshot.getString("TIME"));
                        data.add("CAR CAPACITY - " + documentSnapshot.getString("CARCAPACITY"));
                        data.add("DRIVER NAME - " + documentSnapshot.getString("DRIVERNAME"));

                        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        listView.setAdapter(adapter);
                        if(documentSnapshot.getString("NAME")==null)
                        {

                            data.clear();
                            delete.setVisibility(View.GONE);
                            dbooking.setVisibility(View.VISIBLE);







                        }
                    }
                }
            });



            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fstore.collection("Bookings").document(userid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), firstactivity.class));
                            }
                        }
                    });

                }
            });


        }

    }
