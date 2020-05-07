package com.example.profileinformation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {
    Button verify;
    EditText Otp;
    String id, number;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    FirebaseFirestore fstore;
    String userid;
    TextView resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication);
        Otp = findViewById(R.id.otp);
        firebaseAuth = FirebaseAuth.getInstance();
        verify = findViewById(R.id.verify);
        resend = findViewById(R.id.resend);
        progressDialog=new ProgressDialog(Verification.this);
        fstore=FirebaseFirestore.getInstance();
        resend.setVisibility(View.GONE);
        userid=firebaseAuth.getCurrentUser().getUid();

        number = getIntent().getStringExtra("phone");
        Start();

                verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OTP = Otp.getText().toString();
                if (OTP.isEmpty()) {

                    Toast.makeText(Verification.this, "empty", Toast.LENGTH_SHORT).show();

                } else if (Otp.length() == 6) {

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, OTP);
                    signInWithPhoneAuthCredential(credential);
                }


            }
        });

    }

    private void Start() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                Verification.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_SHORT).show();


                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {

                        id = verificationId;
                        mResendToken = token;
                    }
                }
        );

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),phonenumber.class));
        finish();
        super.onBackPressed();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Toast.makeText(this, "inside phone authentication", Toast.LENGTH_SHORT).show();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.setMessage("Wait......");
                                    progressDialog.show();
                                    Toast.makeText(Verification.this, "successfull", Toast.LENGTH_SHORT).show();
                                    DocumentReference document=fstore.collection("PROFILE").document(userid);
                                    Map<String, Object> num = new HashMap<>();
                                    num.put("PHONE",number);
                                    document.set(num).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                new CountDownTimer(3000, 1000) {

                                                    public void onTick(long millisUntilFinished) {

                                                    }

                                                    public void onFinish() {
                                                        DocumentReference documentReference=fstore.collection("Bookings").document(userid);
                                                        Map<String, Object> phone = new HashMap<>();
                                                        phone.put("PHONE",number);
                                                        documentReference.set(phone,SetOptions.merge());
                                                      progressDialog.dismiss();
                                                      startActivity(new Intent(getApplicationContext(),firstactivity.class));
                                                      finish();
                                                    }
                                                }.start();
                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(Verification.this, "verification failed", Toast.LENGTH_SHORT).show();


                                }
                            }
                        }
                );


    }

}
