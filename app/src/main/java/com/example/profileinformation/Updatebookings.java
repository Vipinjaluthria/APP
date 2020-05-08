package com.example.profileinformation;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Updatebookings extends AppCompatActivity {
    EditText name,additionalluggage,drivername,carnumber,carname,carcapcity,D,T;
    FirebaseFirestore fstore;
    String userid;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView date ,time;
    Button updatebtn;
    AutoCompleteTextView Gender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatebookings);
        Updatebookings.this.setTitle("Update Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name=findViewById(R.id.name);
        additionalluggage=findViewById(R.id.additionalluggage);
        drivername=findViewById(R.id.driver);
        carnumber=findViewById(R.id.carnumber);
        date=findViewById(R.id.date);
        D=findViewById(R.id.D);
        T=findViewById(R.id.T);
        time=findViewById(R.id.time);
        carname=findViewById(R.id.carname);
        Gender=findViewById(R.id.gender);
        updatebtn=findViewById(R.id.button3);
        final String[] gender ={"MALE","FEMALE"};

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice, gender);
        Gender.setThreshold(1);
        Gender.setAdapter(adapter);


        progressDialog =new ProgressDialog(Updatebookings.this);
        fstore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        carcapcity=findViewById(R.id.carcapacity);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        userid=firebaseAuth.getCurrentUser().getUid();
        D.setVisibility(View.GONE);
        T.setVisibility(View.GONE);
        fstore.collection("Bookings").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.getString("DATE")!=null) {

                    name.setText(documentSnapshot.getString("NAME"));

                    carcapcity.setText(documentSnapshot.getString("CARCAPACITY"));
                    additionalluggage.setText(documentSnapshot.getString("ADDITIONALLUGGAGE"));
                    carnumber.setText(documentSnapshot.getString("CARNUMBER"));
                    Gender.setText(documentSnapshot.getString("GENDER"));
                    drivername.setText(documentSnapshot.getString("DRIVERNAME"));
                    carname.setText(documentSnapshot.getString("CARNAME"));
                    time.setText(documentSnapshot.getString("TIME"));
                    T.setText(documentSnapshot.getString("TIME"));
                    D.setText(documentSnapshot.getString("DATE"));
                    date.setText(documentSnapshot.getString("DATE"));
                 }
                else
                {
                    Toast.makeText(Updatebookings.this, "Please add Booking", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),firstactivity.class));

                }




                progressDialog.dismiss();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time();
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String DRIVERNAME = drivername.getText().toString();

                String ADDITIONAL_LUGGAGE = additionalluggage.getText().toString();

                final String NAME = name.getText().toString();
                final String CARCAPACITY = carcapcity.getText().toString();
                final String TIME = T.getText().toString();
                final String DATE = D.getText().toString();
                final String CARNUMBER=carnumber.getText().toString();
                String GENDER =Gender.getText().toString();
                final String CARNAME = carname.getText().toString();
                if ((GENDER.equals("MALE") || GENDER.equals("FEMALE")) && CARNUMBER.length() == 10 && !ADDITIONAL_LUGGAGE.isEmpty() && !CARCAPACITY.isEmpty() && !NAME.isEmpty() && !DRIVERNAME.isEmpty() && !CARNAME.isEmpty())
                {
                    fstore.collection("Bookings").document(userid).update("NAME", NAME);

                    fstore.collection("Bookings").document(userid).update("CARNUMBER", CARNUMBER);
                    fstore.collection("Bookings").document(userid).update("CARNAME", CARNAME);
                    fstore.collection("Bookings").document(userid).update("TIME", TIME);
                    fstore.collection("Bookings").document(userid).update("DATE", DATE);
                    fstore.collection("Bookings").document(userid).update("ADDITIONALLUGGAGE", ADDITIONAL_LUGGAGE);
                    fstore.collection("Bookings").document(userid).update("CARCAPACITY", CARCAPACITY);
                    fstore.collection("Bookings").document(userid).update("GENDER", GENDER);
                    fstore.collection("Bookings").document(userid).update("DRIVERNAME", DRIVERNAME);
                    progressDialog.setTitle("Updating");
                    progressDialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 3000);
                    Toast.makeText(Updatebookings.this, "Done Updating", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(),firstactivity.class));

                }
                else
                {
                    Toast.makeText(Updatebookings.this, "fill the given detials", Toast.LENGTH_SHORT).show();
                }
            }
        });

//
     }

    private void Date() {
        Calendar calendar = Calendar.getInstance();
        int Month = calendar.get(Calendar.MONTH);
        int Year=calendar.get(Calendar.YEAR);
        int Day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Date=dayOfMonth+"/"+month+"/"+year;
                date.setText(Date);
                D.setText(Date);

            }
        },Year,Month,Day);
             datePickerDialog.show();
             datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
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
                time.setText(TIME);

            }
        },hour,minute, DateFormat.is24HourFormat(getApplicationContext() ) );
        timePickerDialog.show();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(getApplicationContext(),firstactivity.class));
        return super.onOptionsItemSelected(item);
    }
}