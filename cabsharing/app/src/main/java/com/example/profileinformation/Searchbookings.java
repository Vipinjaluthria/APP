package com.example.profileinformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Searchbookings extends AppCompatActivity {

    private FirestoreRecyclerAdapter<Modelclass,ProductViewHolder> adapter;
    RecyclerView recyclerView;
    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbookings);
       recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstore = FirebaseFirestore.getInstance();



        Query query = fstore.collection("Bookings");
        FirestoreRecyclerOptions<Modelclass> options = new FirestoreRecyclerOptions.Builder<Modelclass>().setQuery(query, Modelclass.class).build();

                adapter = new FirestoreRecyclerAdapter<Modelclass, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Modelclass model) {
             holder.setlaguage("Laguage - "+model.getLaguage());
             holder.setplace("Place - "+model.getPlace());
             holder.settime("Time - "+model.getTime());
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
    }
