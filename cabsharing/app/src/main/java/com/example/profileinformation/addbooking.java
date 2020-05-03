package com.example.profileinformation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class addbooking extends AppCompatActivity{
    EditText name,carname,drivername,additionalluggage,D,T,carcapcity,carnumber;
    TextView Time,Date;
    String PHONE;
     AutoCompleteTextView Gender;

   FirebaseFirestore fstore;
     Button datebtn;
    FirebaseAuth mfirebase;
     Toolbar toolbar;
    Button timebtn;
    String userid;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbooking);
        name = findViewById(R.id.name);
        timebtn = findViewById(R.id.button4);
        Date = findViewById(R.id.date);
        Gender = findViewById(R.id.gender);
        T = findViewById(R.id.T);
        D = findViewById(R.id.D);
        T.setVisibility(View.GONE);
        D.setVisibility(View.GONE);
        carcapcity = findViewById(R.id.carcapacity);
        toolbar = findViewById(R.id.addtollbar);
        setSupportActionBar(toolbar);


        Time = findViewById(R.id.time);

        carnumber = findViewById(R.id.carnumber);
        Time.setVisibility(View.GONE);
        datebtn = findViewById(R.id.button2);
        mfirebase = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        carname = findViewById(R.id.carname);
        drivername = findViewById(R.id.driver);
        additionalluggage = findViewById(R.id.additionalluggage);
        Date.setVisibility(View.GONE);
        userid = mfirebase.getCurrentUser().getUid();
        check();
        final String[] gender = {"MALE", "FEMALE"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice, gender);
        Gender.setThreshold(1);
        Gender.setAdapter(adapter);

            datebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call();
                }


            });
            timebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time();
                }
            });


            Button confirm = findViewById(R.id.button3);


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String DRIVERNAME = drivername.getText().toString();

                    String ADDITIONAL_LUGGAGE = additionalluggage.getText().toString();

                    final String NAME = name.getText().toString();
                    final String CARCAPACITY = carcapcity.getText().toString();
                    final String TIME = T.getText().toString();
                    final String DATE = D.getText().toString();
                    final String CARNUMBER = carnumber.getText().toString();
                    String GENDER = Gender.getText().toString();



                    final String CARNAME = carname.getText().toString();

                    if (CARNUMBER.isEmpty()) {
                        carnumber.setError("required");
                    }
                    if (NAME.isEmpty()) {
                        name.setError("required");
                    }
                    if (CARNAME.isEmpty()) {
                        carname.setError("required");
                    }
                    if (DRIVERNAME.isEmpty()) {
                        drivername.setError("required");
                    }
                    if (ADDITIONAL_LUGGAGE.isEmpty()) {
                        additionalluggage.setError("required");
                    }

                    if (TIME.isEmpty()) {
                        Time.setError("required");
                        Time.setVisibility(View.GONE);
                        timebtn.setVisibility(View.VISIBLE);
                    }
                    if (DATE.isEmpty()) {
                        Date.setError("required");
                        Date.setVisibility(View.GONE);
                        datebtn.setVisibility(View.VISIBLE);
                    }
                    if (CARCAPACITY.isEmpty()) {
                        carcapcity.setError("required");

                    }
                    if (GENDER.isEmpty()) {
                        Gender.setError("required");

                    } else {
                        GENDER = GENDER.toUpperCase();
                    }
                    if ((GENDER.equals("MALE") || GENDER.equals("FEMALE")) && !CARNUMBER.isEmpty() && !ADDITIONAL_LUGGAGE.isEmpty() && !CARCAPACITY.isEmpty() && !NAME.isEmpty() && !DRIVERNAME.isEmpty() && !CARNAME.isEmpty() && !DATE.isEmpty() && Date.length() == 9 && !TIME.isEmpty() && Time.length() <= 5 && Time.length() >= 4) {
                        Map<String, Object> user = new HashMap<>();
                        DocumentReference myref = fstore.collection("Bookings").document(userid);
                        user.put("DRIVERNAME", DRIVERNAME.toUpperCase());
                        user.put("ADDITIONALLUGGAGE", ADDITIONAL_LUGGAGE.toUpperCase());
                        user.put("NAME", NAME.toUpperCase());
                        user.put("CARCAPACITY", CARCAPACITY.toUpperCase());
                        user.put("TIME", TIME.toUpperCase());
                        user.put("DATE", DATE.toUpperCase());
                        user.put("GENDER", GENDER.toUpperCase());
                        user.put("CARNAME", CARNAME.toUpperCase());
                        user.put("CARNUMBER", CARNUMBER.toUpperCase());
                        user.put("TIMESTAMP", FieldValue.serverTimestamp());
                        myref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(addbooking.this, "confirmed booking", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(addbooking.this, firstactivity.class));
                                } else {
                                    Toast.makeText(addbooking.this, "Error Ocurr", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

            });
        }

    private void check() {
        fstore.collection("PROFILE").document(userid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                assert documentSnapshot != null;
                if(documentSnapshot.getString("PHONE")==null)
                    {
                        Toast.makeText(addbooking.this, "verify your phone number", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),firstactivity.class));

                    }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(getApplicationContext(),firstactivity.class));
        return super.onOptionsItemSelected(item);
    }

    private void time() {
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog=new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String TIME=hourOfDay+":"+minute;
                T.setText(TIME);
               Time.setText(TIME);
               timebtn.setVisibility(View.GONE);
                Time.setVisibility(View.VISIBLE);
            }
        },hour,minute,DateFormat.is24HourFormat(getApplicationContext() ) );
        timePickerDialog.show();


    }

    private void Call() {

        Calendar calendar =Calendar.getInstance();
        int year =calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);


        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int MONTH=month+1;

                String DATE = dayOfMonth + "/" +MONTH + "/" + year;
                Date.setText(DATE);
                D.setText(DATE);
                datebtn.setVisibility(View.GONE);
                Date.setVisibility(View.VISIBLE);
            }
        }, year, month, day
        );
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

    }

}
