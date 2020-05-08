package com.example.profileinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    EditText sendmsg;
    ImageButton sendbtn;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        sendbtn=findViewById(R.id.sendbutton);
        userid=firebaseAuth.getCurrentUser().getUid();
        sendmsg=findViewById(R.id.sendmsg);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SENDMESSAGE=sendmsg.getText().toString();
                Map<String,Object> data =new HashMap<>();
                DocumentReference documentReference =fstore.collection("CHAT").document(userid).collection("rooms").document("vipin");
                data.put("NAME",SENDMESSAGE);
                documentReference.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ChatActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });



    }
}
