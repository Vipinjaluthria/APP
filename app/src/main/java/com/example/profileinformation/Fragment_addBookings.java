package com.example.profileinformation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Fragment_addBookings extends Fragment {
    EditText D, T;
    TextInputLayout name, carname, drivername, additionalluggage, carcapcity, carnumber;
    TextView Time, Date;
    String PHONE;
    AutoCompleteTextView Gender;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore fstore;
    Button datebtn;
    FirebaseAuth mfirebase;
    String id;
    Toolbar toolbar;
    Button timebtn;
    String userid,Phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_addbooking, container, false);
        name = view.findViewById(R.id.name);
        timebtn = view.findViewById(R.id.button4);
        Date = view.findViewById(R.id.date);
        Gender = view.findViewById(R.id.gender);
        T = view.findViewById(R.id.T);
        D = view.findViewById(R.id.D);
        T.setVisibility(View.GONE);
        D.setVisibility(View.GONE);
        carcapcity = view.findViewById(R.id.carcapacity);



        Time = view.findViewById(R.id.time);

        carnumber = view.findViewById(R.id.carnumber);
        Time.setVisibility(View.GONE);
        datebtn = view.findViewById(R.id.button2);
        mfirebase = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        carname = view.findViewById(R.id.carname);
        drivername = view.findViewById(R.id.driver);
        additionalluggage = view.findViewById(R.id.additionalluggage);
        Date.setVisibility(View.GONE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
// Build a GoogleSignInClient with the options specified by g

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


       final GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getContext());
        userid=mfirebase.getCurrentUser().getUid();
        id=account.getId();
        check();
        final String[] gender = {"MALE", "FEMALE"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_singlechoice, gender);
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


        Button confirm = view.findViewById(R.id.button3);
        fstore.collection("PHONE").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                Phone=documentSnapshot.getString("PHONE");

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String DRIVERNAME = drivername.getEditText().getText().toString();

                String ADDITIONAL_LUGGAGE = additionalluggage.getEditText().getText().toString();

                final String NAME = name.getEditText().getText().toString();
                final String CARCAPACITY = carcapcity.getEditText().getText().toString();
                final String TIME = T.getText().toString();
                final String DATE = D.getText().toString();
                final String CARNUMBER = carnumber.getEditText().getText().toString();
                String GENDER = Gender.getText().toString();
                final String CARNAME = carname.getEditText().getText().toString();

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
                if ((GENDER.equals("MALE") || GENDER.equals("FEMALE")) && !CARNUMBER.isEmpty() && !ADDITIONAL_LUGGAGE.isEmpty() && !CARCAPACITY.isEmpty() && CARNUMBER.length() == 10 && !NAME.isEmpty() && !DRIVERNAME.isEmpty() && !CARNAME.isEmpty() && !DATE.isEmpty() && Date.length() == 9 && !TIME.isEmpty() && Time.length() <= 5 && Time.length() >= 4) {
                    Map<String, Object> user = new HashMap<>();
                    DocumentReference myref = fstore.collection("BOOK").document(id);
                    user.put("DRIVERNAME", DRIVERNAME.toUpperCase());
                    user.put("ADDITIONALLUGGAGE", ADDITIONAL_LUGGAGE.toUpperCase());
                    user.put("NAME", NAME.toUpperCase());
                    user.put("PHONE",Phone);
                    user.put("CARCAPACITY", CARCAPACITY.toUpperCase());
                    user.put("TIME", TIME.toUpperCase());
                    user.put("DATE", DATE.toUpperCase());
                    user.put("GENDER", GENDER.toUpperCase());
                    user.put("CARNAME", CARNAME.toUpperCase());
                    user.put("CARNUMBER", CARNUMBER.toUpperCase());
                    user.put("TIMESTAMP", FieldValue.serverTimestamp());
                    myref.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "confirmed booking", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), firstactivity.class));
                            } else {
                                Toast.makeText(getContext(), "Error Ocurr", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });
        return view;
    }

    /*private void check() {
        fstore.collection("PROFILE").document(userid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                assert documentSnapshot != null;
                if (documentSnapshot.getString("PHONE") == null) {
                    Toast.makeText(getContext(), "verify your phone number", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), firstactivity.class));

                }
            }
        });

    }*/
    private void check() {

        fstore.collection("PHONE").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.getString("PHONE")==null) {
                    Toast.makeText(getContext(), "verify phone number", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),firstactivity.class));
                }


            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(getContext(), firstactivity.class));
        return super.onOptionsItemSelected(item);
    }

    private void time() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String TIME = hourOfDay + ":" + minute;
                T.setText(TIME);
                Time.setText(TIME);
                timebtn.setVisibility(View.GONE);
                Time.setVisibility(View.VISIBLE);
            }
        }, hour, minute, DateFormat.is24HourFormat(getContext()));
        timePickerDialog.show();


    }

    private void Call() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);


        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int MONTH = month + 1;

                String DATE = dayOfMonth + "/" + MONTH + "/" + year;
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




