package com.example.profileinformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Searchbookings extends AppCompatActivity {

    private FirestoreRecyclerAdapter<Modelclass, ProductViewHolder> adapter;
    RecyclerView recyclerView;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbookings);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstore = FirebaseFirestore.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Query query = fstore.collection("Bookings");
        FirestoreRecyclerOptions<Modelclass> options = new FirestoreRecyclerOptions.Builder<Modelclass>().setQuery(query, Modelclass.class).build();
        adapter = new FirestoreRecyclerAdapter<Modelclass, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Modelclass model) {
                holder.setCARNAME("CARNAME - " + model.getCARNAME());
                holder.setCONTACT("CONTACT - " + model.getCONTACT());
                holder.setDRIVERNAME("DRIVER NAME - " + model.getDRIVERNAME());
                holder.setNAME("NAME - " + model.getNAME());
                holder.setADDITIONALLUGGAGE("ADDITIONAL LUGGAGE - " + model.getADDITIONALLUGGAGE());
                holder.setDATE("DATE - " + model.getDATE());
                holder.setCARCAPACITY("CAPACITY - " + model.getCARCAPACITY());
                holder.setGENDER("GENDER - " + model.getGENDER());
                holder.setTIME("TIME - " + model.getTIME());
                holder.setCARNUMBER("CAR NUMBER - " + model.getCARNUMBER());

            }


            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitems, parent, false);
                return new ProductViewHolder(view);
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
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
                 fstore.collection("Bookings").whereEqualTo("DATE",query).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if(task.isSuccessful())
                         {
                         }
                         else
                         {

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


