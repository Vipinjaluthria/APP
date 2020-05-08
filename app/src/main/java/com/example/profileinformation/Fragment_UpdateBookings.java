package com.example.profileinformation;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Fragment_UpdateBookings extends Fragment {
    EditText name,additionalluggage,drivername,carnumber,carname,carcapcity,D,T;
    FirebaseFirestore fstore;
    String userid;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView date ,time;
    Button updatebtn;
    AutoCompleteTextView Gender;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_updatebookings,container,false);
        name=view.findViewById(R.id.name);
        additionalluggage=view.findViewById(R.id.additionalluggage);
        drivername=view.findViewById(R.id.driver);
        carnumber=view.findViewById(R.id.carnumber);
        date=view.findViewById(R.id.date);
        D=view.findViewById(R.id.D);
        T=view.findViewById(R.id.T);
        time=view.findViewById(R.id.time);
        carname=view.findViewById(R.id.carname);
        Gender=view.findViewById(R.id.gender);
        updatebtn=view.findViewById(R.id.button3);
        final String[] gender ={"MALE","FEMALE"};

        ArrayAdapter<String> adapter= new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_singlechoice, gender);
        Gender.setThreshold(1);
        Gender.setAdapter(adapter);

        Context context=getContext();
        progressDialog =new ProgressDialog(context);
        fstore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        carcapcity=view.findViewById(R.id.carcapacity);
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
                    carname.setText(" "+documentSnapshot.getString("CARNAME"));
                    time.setText(" "+documentSnapshot.getString("TIME"));
                    T.setText(documentSnapshot.getString("TIME"));
                    D.setText(documentSnapshot.getString("DATE"));
                    date.setText(documentSnapshot.getString("DATE"));
                }
                else
                {
                    Toast.makeText(getContext(), "Please add Booking", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(),firstactivity.class));

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
                    Toast.makeText(getContext(), "Done Updating", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getContext(),firstactivity.class));

                }
                else
                {
                    Toast.makeText(getContext(), "fill the given detials", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
    private void Date() {
        Calendar calendar = Calendar.getInstance();
        int Month = calendar.get(Calendar.MONTH);
        int Year=calendar.get(Calendar.YEAR);
        int Day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

        TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(),new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String TIME=hourOfDay+":"+minute;
                T.setText(TIME);
                time.setText(TIME);

            }
        },hour,minute, DateFormat.is24HourFormat(getContext() ) );
        timePickerDialog.show();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(getContext(),firstactivity.class));
        return super.onOptionsItemSelected(item);
    }
}
