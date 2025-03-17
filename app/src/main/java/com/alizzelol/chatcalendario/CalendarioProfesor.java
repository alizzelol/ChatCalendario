package com.alizzelol.chatcalendario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarioProfesor extends AppCompatActivity {

    private GridView calendarGrid;
    private Calendar calendar;
    private List<Date> days;
    private CalendarAdapterPro calendarAdapter;
    private TextView textMesAño;
    private String username; // Variable para almacenar el nombre de usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_profesor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarGrid = findViewById(R.id.calendarGrid);
        calendar = Calendar.getInstance();
        textMesAño = findViewById(R.id.textMesAno);

        ImageButton buttonPrevMonth = findViewById(R.id.buttonPrevMonth);
        ImageButton buttonNextMonth = findViewById(R.id.buttonNextMonth);
        Button btnChat = findViewById(R.id.btnChat); // Obtener el botón Chat

        // Obtener el nombre de usuario del Intent
        username = getIntent().getStringExtra("username");

        buttonPrevMonth.setOnClickListener(v -> mostrarMesAnterior());
        buttonNextMonth.setOnClickListener(v -> mostrarMesSiguiente());

        generateCalendar();
        updateCalendar();
        actualizarTextoMesAño();

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarioProfesor.this, ChatActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    private void mostrarMesAnterior() {
        calendar.add(Calendar.MONTH, -1);
        generateCalendar();
        updateCalendar();
        actualizarTextoMesAño();
    }

    private void mostrarMesSiguiente() {
        calendar.add(Calendar.MONTH, 1);
        generateCalendar();
        updateCalendar();
        actualizarTextoMesAño();
    }

    private void actualizarTextoMesAño() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM фигур", new Locale("es", "ES"));
        String mesAño = sdf.format(calendar.getTime());
        textMesAño.setText(mesAño.toUpperCase());
    }

    private void generateCalendar() {
        days = new ArrayList<>();
        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        tempCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);
        for (int i = 0; i < 42; i++) {
            days.add(tempCalendar.getTime());
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void updateCalendar() {
        calendarAdapter = new CalendarAdapterPro(this, days, calendar);
        calendarGrid.setAdapter(calendarAdapter);
    }
}