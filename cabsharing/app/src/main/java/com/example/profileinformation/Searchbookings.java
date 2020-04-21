package com.example.profileinformation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class Searchbookings extends AppCompatActivity {

    RecyclerView recyclerView;
    String userid;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;

    public ProgressDialog dialog;
    List<Modelclass> c;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbookings);
        recyclerView = findViewById(R.id.recycle);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstore = FirebaseFirestore.getInstance();

        userid = firebaseAuth.getCurrentUser().getUid();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         dialog = new ProgressDialog(Searchbookings.this);
        dialog.setMessage("Loading.....");
        dialog.show();
        fstore.collection("Bookings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                c = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    dialog.dismiss();

                    Modelclass modelclass = new Modelclass("CARNUMBER - " + documentSnapshot.getString("CARNUMBER"), "GENDER - " + documentSnapshot.
                            getString("GENDER"), "NAME - " + documentSnapshot.
                            getString("NAME"), "CONTACT - " + documentSnapshot.
                            getString("CONTACT"), " DRIVERNAME - " + documentSnapshot.
                            getString("DRIVERNAME"), "ADDITIONAL LUGGAGE - " + documentSnapshot.
                            getString("ADDITIONAL LUGGAGE"), "CAB NAME - " + documentSnapshot.
                            getString("CABNAME"), "DATE - " + documentSnapshot.
                            getString("DATE"), "TIME - " + documentSnapshot.
                            getString("TIME"), "CAR CAPACITY - " + documentSnapshot.
                            getString("CARCAPACITY - "));

                    c.add(modelclass);
                }
                adapter = new Adapter(c);
                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

     return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searchitem, menu);
       MenuItem menuItem =menu.findItem(R.id.search_bar);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(menuItem);
         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 fstore.collection("Bookings").whereEqualTo("NAME",query.toUpperCase()).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             dialog.setTitle("Searching....");
                             dialog.show();

                             c.clear();
                             for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                 dialog.dismiss();
                                 Modelclass modelclass = new Modelclass("CARNUMBER - " + documentSnapshot.getString("CARNUMBER"), "GENDER - " + documentSnapshot.
                                         getString("GENDER"), "NAME - " + documentSnapshot.
                                         getString("NAME"), "CONTACT - " + documentSnapshot.
                                         getString("CONTACT"), " DRIVERNAME - " + documentSnapshot.
                                         getString("DRIVERNAME"), "ADDITIONAL LUGGAGE - " + documentSnapshot.
                                         getString("ADDITIONAL LUGGAGE"), "CAB NAME - " + documentSnapshot.
                                         getString("CABNAME"), "DATE - " + documentSnapshot.
                                         getString("DATE"), "TIME - " + documentSnapshot.
                                         getString("TIME"), "CAR CAPACITY - " + documentSnapshot.
                                         getString("CARCAPACITY - "));

                                 c.add(modelclass);
                             }
                             adapter = new Adapter(c);

                             recyclerView.setAdapter(adapter);

                         }
                         else {
                             dialog.dismiss();
                             Toast.makeText(Searchbookings.this, "Sorry Friend", Toast.LENGTH_SHORT).show();
                         }

                     }

                 });
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 return false;
             }
         });

        return super.onCreateOptionsMenu(menu);
    }
}


