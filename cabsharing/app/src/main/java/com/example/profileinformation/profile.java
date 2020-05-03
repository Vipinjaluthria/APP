package com.example.profileinformation;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;


import java.time.LocalDate;
import java.util.Objects;

public class profile extends AppCompatActivity{
     FirebaseFirestore fstore;
     String userid;
     TextView name,email;
     TextView NAME,EMAIL,PHONE;
     ImageView  imageView;
     FirebaseAuth mfirebase;
     TelephonyManager telephonyManager;
     Button logout;
     GoogleSignInClient mGoogleSignInClient;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fstore=FirebaseFirestore.getInstance();
        mfirebase=FirebaseAuth.getInstance();
        NAME=findViewById(R.id.name);
        PHONE=findViewById(R.id.phone);
        EMAIL=findViewById(R.id.email);
        imageView=findViewById(R.id.imageView3);
        logout=findViewById(R.id.button);
        email=findViewById(R.id.textView4);
        name=findViewById(R.id.textView3);

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        assert acct != null;
        final Uri personPhoto=acct.getPhotoUrl();
        NAME.setText(" "+acct.getDisplayName());
        EMAIL.setText(" "+acct.getEmail());

        Glide.with(this).load(personPhoto).into(imageView);



        userid= Objects.requireNonNull(mfirebase.getCurrentUser()).getUid();
        DocumentReference myref =fstore.collection("PROFILE").document(userid);
        myref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
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
// Build a GoogleSignInClient with the options specified by g

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent =new Intent(this,firstactivity.class);

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(profile.this,googlesignin.class));
                        revokeAccess();
                        // ...
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(profile.this, "account deleted", Toast.LENGTH_SHORT).show();

                        // ...
                    }
                });
    }


}

