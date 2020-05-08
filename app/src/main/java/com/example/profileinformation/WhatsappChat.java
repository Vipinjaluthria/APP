package com.example.profileinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class WhatsappChat extends AppCompatActivity {
    String PHONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp_chat);
        Intent intent = getIntent();

    }
}
