package com.example.profileinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addbooking extends AppCompatActivity {
   EditText name,contact,carname,drivername,additionallaguage;
   FirebaseAuth mfirebase;
   FirebaseFirestore fstore;
   DatePicker datePicker;
   Button confirm;
   String userid;
   GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbooking);
        name=findViewById(R.id.name);
        datePicker=findViewById(R.id.datepicker);
        contact=findViewById(R.id.contact);
        mfirebase=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
       carname=findViewById(R.id.carname);
       drivername=findViewById(R.id.driver);
       additionallaguage=findViewById(R.id.additionallaguage);



        confirm=findViewById(R.id.button3);

        userid=mfirebase.getCurrentUser().getUid();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
// Build a GoogleSignInClient with the options specified by g

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        assert acct != null;

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String DRIVERNAME=drivername.getText().toString();

                final String   ADDITIONAL_LAGUAGE=additionallaguage.getText().toString();
                final String NAME= name.getText().toString();
                final String CONTACT=contact.getText().toString();
                final String CARNAME=carname.getText().toString();
                if(CONTACT.isEmpty())
                {
                    contact.setError("required");
                }
                if(NAME.isEmpty())
                {
                    name.setError("required");
                }
                if(CARNAME.isEmpty())
                {
                    carname.setError("required");
                }
                if(DRIVERNAME.isEmpty())
                {
                    drivername.setError("required");
                }
                if(ADDITIONAL_LAGUAGE.isEmpty())
                {
                    additionallaguage.setError("required");
                }
        if( !NAME.isEmpty() && !CONTACT.isEmpty() && !DRIVERNAME.isEmpty() && !CARNAME.isEmpty() && !ADDITIONAL_LAGUAGE.isEmpty() ) {
            Map<String, Object> user = new HashMap<>();
            DocumentReference myref = fstore.collection("Bookings").document(userid);
            user.put("DRIVERNAME",DRIVERNAME);
            user.put("ADDITIONALLAGUAGE",ADDITIONAL_LAGUAGE);
            user.put("NAME",NAME);
            user.put("CONTACT",CONTACT);

            user.put("CARNAME",CARNAME);
            myref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(addbooking.this, "confirmed booking", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(addbooking.this,firstactivity.class));
                    } else {
                        Toast.makeText(addbooking.this, "Error Ocurr", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }


            }
        });








    }
}
