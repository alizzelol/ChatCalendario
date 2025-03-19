package com.alizzelol.ChatCalendario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.alizzelol.chatcalendario.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetallesUsuarioActivity extends AppCompatActivity {

    private TextView textViewNombre, textViewApellido, textViewEmail, textViewTelefono, textViewRol;
    private Button buttonEliminar, buttonEditar;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_usuario);

        textViewNombre = findViewById(R.id.textViewNombre);
        textViewApellido = findViewById(R.id.textViewApellido);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewTelefono = findViewById(R.id.textViewTelefono);
        textViewRol = findViewById(R.id.textViewRol);
        buttonEliminar = findViewById(R.id.buttonEliminar);
        buttonEditar = findViewById(R.id.buttonEditar);

        db = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra("userId");

        cargarDetallesUsuario();

        buttonEliminar.setOnClickListener(v -> eliminarUsuario());
        buttonEditar.setOnClickListener(v -> editarUsuario());
    }

    private void cargarDetallesUsuario() {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        textViewNombre.setText("Nombre: " + documentSnapshot.getString("nombre"));
                        textViewApellido.setText("Apellido: " + documentSnapshot.getString("apellido"));
                        textViewEmail.setText("Email: " + documentSnapshot.getString("email"));
                        textViewTelefono.setText("Teléfono: " + documentSnapshot.getString("telefono"));
                        textViewRol.setText("Rol: " + documentSnapshot.getString("rol"));
                    } else {
                        Toast.makeText(this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar detalles del usuario.", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    private void eliminarUsuario() {
        db.collection("users").document(userId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Usuario eliminado con éxito.", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al eliminar usuario.", Toast.LENGTH_SHORT).show();
                });
    }

    private void editarUsuario() {
        Intent intent = new Intent(DetallesUsuarioActivity.this, EditarUsuarioActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}