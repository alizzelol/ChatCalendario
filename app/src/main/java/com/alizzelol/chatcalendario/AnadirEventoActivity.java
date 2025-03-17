package com.alizzelol.chatcalendario;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AnadirEventoActivity extends AppCompatActivity {

    private EditText editTextTitulo, editTextDescripcion, editTextHora, editTextTipoEvento;
    private Button buttonFecha, buttonGuardar;
    private Calendar calendar;
    private FirebaseFirestore db;
    private Date fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_evento);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextHora = findViewById(R.id.editTextHora);
        editTextTipoEvento = findViewById(R.id.editTextTipoEvento);
        buttonFecha = findViewById(R.id.buttonFecha);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        calendar = Calendar.getInstance();
        db = FirebaseFirestore.getInstance();

        buttonFecha.setOnClickListener(v -> mostrarDatePicker());
        buttonGuardar.setOnClickListener(v -> guardarEvento());
    }

    private void mostrarDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    fechaSeleccionada = calendar.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    buttonFecha.setText(sdf.format(fechaSeleccionada));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void guardarEvento() {
        String titulo = editTextTitulo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String hora = editTextHora.getText().toString();
        String tipo = editTextTipoEvento.getText().toString().toLowerCase();

        if (titulo.isEmpty() || descripcion.isEmpty() || hora.isEmpty() || fechaSeleccionada == null || tipo.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            sdf.parse(hora);
        } catch (ParseException e) {
            Toast.makeText(this, "Formato de hora incorrecto (HH:mm).", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> evento = new HashMap<>();
        evento.put("título", titulo);
        evento.put("descripción", descripcion);
        evento.put("fecha", fechaSeleccionada);
        evento.put("hora", hora);
        evento.put("tipo", tipo);

        db.collection("eventos").add(evento)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Evento guardado con éxito.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar el evento.", Toast.LENGTH_SHORT).show());
    }
}