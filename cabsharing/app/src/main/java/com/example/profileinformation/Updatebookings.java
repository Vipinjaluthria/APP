package com.example.profileinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Updatebookings extends AppCompatActivity {
    Button cappcity, luggage, carnumber, button8,driverbtn;
    FirebaseFirestore fstore;
    FirebaseAuth mfirebase;
    EditText ecapacity, eluggage, ecarnumber,edrivername;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatebookings);
        cappcity = findViewById(R.id.capacity);
        luggage = findViewById(R.id.luggage);
        carnumber = findViewById(R.id.number);
        driverbtn=findViewById(R.id.button9);
        ecapacity = findViewById(R.id.CAPACITY);
        ecarnumber = findViewById(R.id.NUMBER);
        eluggage = findViewById(R.id.edit);
        button8 = findViewById(R.id.button8);
        edrivername=findViewById(R.id.drivername);
        mfirebase = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        eluggage.setVisibility(View.GONE);
        button8.setVisibility(View.GONE);
        edrivername.setVisibility(View.GONE);
        ecarnumber.setVisibility(View.GONE);

        ecapacity.setVisibility(View.GONE);
        userid = mfirebase.getCurrentUser().getUid();
        cappcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cappcity.setVisibility(View.GONE);
                luggage.setVisibility(View.GONE);
                carnumber.setVisibility(View.GONE);
                button8.setVisibility(View.VISIBLE);
                driverbtn.setVisibility(View.GONE);
                ecapacity.setVisibility(View.VISIBLE);
                button8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> user = new HashMap<>();
                        final String CARCAPCITY = ecapacity.getText().toString();
                        if (CARCAPCITY.isEmpty()) {
                            ecapacity.setError("required");
                        } else {
                            fstore.collection("Bookings").document(userid).update("CARCAPACITY", CARCAPCITY).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Updatebookings.this, "DONE", Toast.LENGTH_SHORT).show();
                                        cappcity.setVisibility(View.VISIBLE);
                                        luggage.setVisibility(View.VISIBLE);
                                        carnumber.setVisibility(View.VISIBLE);
                                        driverbtn.setVisibility(View.VISIBLE);
                                        ecapacity.setVisibility(View.GONE);

                                    }
                                    else
                                    {
                                        Toast.makeText(Updatebookings.this, "NO BOOKING FOUND", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }


                    }
                });
            }

        });
        driverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cappcity.setVisibility(View.GONE);
                luggage.setVisibility(View.GONE);
                carnumber.setVisibility(View.GONE);
                button8.setVisibility(View.VISIBLE);
                edrivername.setVisibility(View.VISIBLE);
                driverbtn.setVisibility(View.GONE);
                button8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String DRIVERNAME = edrivername.getText().toString();
                        if (DRIVERNAME.isEmpty()) {
                            edrivername.setError("required");
                        } else {
                            fstore.collection("Bookings").document(userid).update("DRIVERNAME", DRIVERNAME).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Updatebookings.this, "DONE", Toast.LENGTH_SHORT).show();
                                        cappcity.setVisibility(View.VISIBLE);
                                        luggage.setVisibility(View.VISIBLE);
                                        carnumber.setVisibility(View.VISIBLE);
                                        edrivername.setVisibility(View.GONE);
                                        driverbtn.setVisibility(View.VISIBLE);

                                    }
                                    else
                                    {
                                        Toast.makeText(Updatebookings.this, "NO BOOKING FOUND", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }


                    }
                });
            }

        });

        luggage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cappcity.setVisibility(View.GONE);
                luggage.setVisibility(View.GONE);
                driverbtn.setVisibility(View.GONE);
                button8.setVisibility(View.VISIBLE);
                carnumber.setVisibility(View.GONE);
                eluggage.setVisibility(View.VISIBLE);
                button8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String LUGGAGE = eluggage.getText().toString();
                        if(LUGGAGE.isEmpty())
                        {
                            eluggage.setError("required");
                        }
                            else {
                            fstore.collection("Bookings").document(userid).update("ADDITIONALLUGGAGE", LUGGAGE).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Updatebookings.this, "DONE", Toast.LENGTH_SHORT).show();
                                        cappcity.setVisibility(View.VISIBLE);
                                        luggage.setVisibility(View.VISIBLE);
                                        carnumber.setVisibility(View.VISIBLE);
                                        eluggage.setVisibility(View.GONE);
                                        driverbtn.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        Toast.makeText(Updatebookings.this, "NO BOOKING FOUND", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });
            }
        });
        carnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cappcity.setVisibility(View.GONE);
                luggage.setVisibility(View.GONE);
                driverbtn.setVisibility(View.GONE);
                carnumber.setVisibility(View.GONE);
                button8.setVisibility(View.VISIBLE);
                ecarnumber.setVisibility(View.VISIBLE);
                button8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> user = new HashMap<>();
                        final String CARNUMBER = ecarnumber.getText().toString();
                        if(CARNUMBER.isEmpty())
                        {
                            carnumber.setError("required");
                        }
                        else {

                            fstore.collection("Bookings").document(userid).update("CARNUMBER", CARNUMBER).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Updatebookings.this, "DONE", Toast.LENGTH_SHORT).show();
                                        cappcity.setVisibility(View.VISIBLE);
                                        luggage.setVisibility(View.VISIBLE);
                                        carnumber.setVisibility(View.VISIBLE);
                                        driverbtn.setVisibility(View.VISIBLE);
                                        ecarnumber.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        Toast.makeText(Updatebookings.this, "NO BOOKING FOUND", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }


                    }
                });
            }
        });
    }
}