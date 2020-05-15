package com.example.profileinformation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Delete extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String userid;
    ArrayAdapter adapter;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;
    TextView dbooking;
    ListView listView;

    Button delete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_delete, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        listView = view.findViewById(R.id.listview);
        delete = view.findViewById(R.id.Delete);
        dbooking = view.findViewById(R.id.nobookingtodelete);
        dbooking.setVisibility(View.GONE);

        Context context = getContext();


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading ......");
        progressDialog.show();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
// Build a GoogleSignInClient with the options specified by g

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getContext());

        progressDialog.show();
        userid=account.getId();
        final List<String> data = new ArrayList<>();

        fstore.collection("BOOK").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();
                    final String NAME = documentSnapshot.getString("NAME");
                    data.add("NAME -" + NAME);
                    data.add("CAB NAME - " + documentSnapshot.getString("CARNAME"));
                    data.add("GENDER - " + documentSnapshot.getString("GENDER"));
                    data.add("CAR NUMBER - " + documentSnapshot.getString("CARNUMBER"));
                    data.add("ADDITIONAL LUGGAGE - " + documentSnapshot.getString("ADDITIONALLUGGAGE"));
                    data.add("DATE - " + documentSnapshot.getString("DATE"));
                    data.add("TIME - " + documentSnapshot.getString("TIME"));
                    data.add("CAR CAPACITY - " + documentSnapshot.getString("CARCAPACITY"));
                    data.add("DRIVER NAME - " + documentSnapshot.getString("DRIVERNAME"));

                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    listView.setAdapter(adapter);
                    if (documentSnapshot.getString("NAME") == null) {

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
                fstore.collection("BOOK").document(userid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getContext(), firstactivity.class));
                        }
                    }
                });

            }
        });


        return view;


    }
}

