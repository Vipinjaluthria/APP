package com.example.profileinformation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class Fragment_Profile extends Fragment {
    FirebaseFirestore fstore;
    Toolbar profiletoolbar;
    String userid;
    TextView name,email;
    TextView NAME,EMAIL,PHONE;
    ImageView imageView;
    FirebaseAuth mfirebase;
    TelephonyManager telephonyManager;
    Button logout;
    GoogleSignInClient mGoogleSignInClient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_profile,container,false);
        fstore=FirebaseFirestore.getInstance();
        mfirebase=FirebaseAuth.getInstance();
        NAME=view.findViewById(R.id.name);
        PHONE=view.findViewById(R.id.phone);
        EMAIL=view.findViewById(R.id.email);
        imageView=view.findViewById(R.id.imageView3);
        logout=view.findViewById(R.id.button);
        email=view.findViewById(R.id.textView4);
        name=view.findViewById(R.id.textView3);


        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        assert acct != null;
        final Uri personPhoto=acct.getPhotoUrl();
        NAME.setText(" "+acct.getDisplayName());
        EMAIL.setText(" "+acct.getEmail());

        Glide.with(getContext()).load(personPhoto).into(imageView);



        userid= Objects.requireNonNull(mfirebase.getCurrentUser()).getUid();
        DocumentReference myref =fstore.collection("PROFILE").document(userid);
        myref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;

                PHONE.setText(" "+documentSnapshot.getString("PHONE"));


            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        return view;


    }


    private void signOut() {

        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(getContext(),googlesignin.class));
                revokeAccess();
            }
        });

    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "SignOut", Toast.LENGTH_SHORT).show();

                        // ...
                    }
                });
    }



}

