package com.example.profileinformation;

import android.app.ProgressDialog;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class phonenumber extends AppCompatActivity {
    Button confirm;
    EditText phone;
    FirebaseAuth firebaseAuth;
    CountryCodePicker countryCodePicker;
    FirebaseFirestore fstore;

    ProgressDialog progressDialog;
    TextView p;
    String userid;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumber);
        fstore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        phone = findViewById(R.id.editText);
        confirm = findViewById(R.id.phone);
        progressDialog = new ProgressDialog(phonenumber.this);
        progressDialog.create();
        progressDialog.setMessage("Wait.......");
        progressDialog.show();
        userid=firebaseAuth.getCurrentUser().getUid();

        progressDialog.dismiss();
        countryCodePicker = findViewById(R.id.country);
        countryCodePicker.registerCarrierNumberEditText(phone);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone = countryCodePicker.getFullNumberWithPlus().replace(" ", "");
                if (phone.getText().toString().isEmpty()) {
                    phone.setError("Please fill the phone");
                } else if (phone.getText().toString().replace(" ", "").length() != 10) {
                    Toast.makeText(phonenumber.this, "enter the valid number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Verification.class);
                    intent.putExtra("phone", Phone);
                    intent.putExtra("USERID",userid);
                    startActivity(intent);
                    finish();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),firstactivity.class));

        super.onBackPressed();
    }
}




