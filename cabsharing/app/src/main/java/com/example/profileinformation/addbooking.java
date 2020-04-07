package com.example.profileinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
   EditText time,laguage,place;
   FirebaseAuth mfirebase;
   FirebaseFirestore fstore;
   Button confirm;
   String userid,username;
   GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbooking);
        time=findViewById(R.id.editText);
        laguage=findViewById(R.id.editText2);
        mfirebase=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        place=findViewById(R.id.editText3);

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
        username=acct.getGivenName();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String TIME= time.getText().toString();
                final String Laguage=laguage.getText().toString();
                final String PLACE=place.getText().toString();
                if(PLACE.isEmpty())
                {
                    place.setError("required");
                }
                if(TIME.isEmpty())
                {
                    time.setError("required");
                }
        if( !TIME.isEmpty() && !PLACE.isEmpty()) {
            Map<String, Object> user = new HashMap<>();
            DocumentReference myref = fstore.collection(username).document(userid);
            user.put("time",TIME);
            user.put("place",PLACE);
            user.put("Laguage",Laguage);
            myref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(addbooking.this, "confirmed booking", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(addbooking.this, "Error Ocurr", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
        else
        {
            laguage.setError("laguage should be 0 to 10");
        }

            }
        });








    }
}
