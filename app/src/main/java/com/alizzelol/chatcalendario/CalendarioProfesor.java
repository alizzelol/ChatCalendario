package com.alizzelol.chatcalendario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class CalendarioProfesor extends AppCompatActivity {

    private Button btnChat;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_profesor);
        btnChat = findViewById(R.id.btnChat);
        username = getIntent().getStringExtra("username");

        btnChat.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarioProfesor.this, ChatActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}