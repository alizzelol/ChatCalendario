package com.alizzelol.chatcalendario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.alizzelol.chatcalendario.chat.ChatActivity;

public class CalendarioPadres extends AppCompatActivity {

    private Button btnChat;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_padres);
        btnChat = findViewById(R.id.btnChat);
        username = getIntent().getStringExtra("username");

        btnChat.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarioPadres.this, ChatActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}