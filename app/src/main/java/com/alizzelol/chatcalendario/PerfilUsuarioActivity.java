package com.alizzelol.chatcalendario;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextApellido, editTextEmail;
    private EditText editTextTelefono, editTextPassword, etConfirmPassword;
    private Button buttonGuardar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userId;
    private DocumentSnapshot documentSnapshotUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextPassword = findViewById(R.id.editTextPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d("PerfilUsuarioActivity", "Usuario autenticado: " + currentUser.getUid());
            userId = currentUser.getUid();
            cargarDatosUsuario();
        } else {
            Log.d("PerfilUsuarioActivity", "Usuario no autenticado.");
            Toast.makeText(this, "Usuario no autenticado.", Toast.LENGTH_SHORT).show();
            finish();
        }

        userId = getIntent().getStringExtra("userId"); // Obtener el userId del Intent
        if (userId != null) {
            cargarDatosUsuario();
        } else {
            Toast.makeText(this, "Error: userId no proporcionado.", Toast.LENGTH_SHORT).show();
            finish();
        }

        buttonGuardar.setOnClickListener(v -> guardarCambios());
    }

    private void cargarDatosUsuario() {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        documentSnapshotUsuario = documentSnapshot;
                        editTextNombre.setText(documentSnapshot.getString("nombre"));
                        editTextApellido.setText(documentSnapshot.getString("apellido"));
                        editTextEmail.setText(documentSnapshot.getString("email"));
                        editTextTelefono.setText(documentSnapshot.getString("telefono"));
                    } else {
                        Toast.makeText(this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar datos del usuario.", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    private void guardarCambios() {
        if (documentSnapshotUsuario == null) {
            Toast.makeText(this, "Error: No se cargaron los datos del usuario.", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = editTextPassword.getText().toString();
        String password2 = etConfirmPassword.getText().toString();

        if (!password.isEmpty() && !password.equals(password2)) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> usuarioActualizado = new HashMap<>();

        if (!editTextNombre.getText().toString().equals(documentSnapshotUsuario.getString("nombre"))) {
            usuarioActualizado.put("nombre", editTextNombre.getText().toString());
        }
        if (!editTextApellido.getText().toString().equals(documentSnapshotUsuario.getString("apellido"))) {
            usuarioActualizado.put("apellido", editTextApellido.getText().toString());
        }
        if (!editTextEmail.getText().toString().equals(documentSnapshotUsuario.getString("email"))) {
            usuarioActualizado.put("email", editTextEmail.getText().toString());
        }
        if (!editTextTelefono.getText().toString().equals(documentSnapshotUsuario.getString("telefono"))) {
            usuarioActualizado.put("telefono", editTextTelefono.getText().toString());
        }

        if (!usuarioActualizado.isEmpty()) {
            db.collection("users").document(userId)
                    .update(usuarioActualizado)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Usuario actualizado con éxito.", Toast.LENGTH_SHORT).show();
                        if (!password.isEmpty()) {
                            actualizarContraseña(password);
                        } else {
                            finish();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error al actualizar usuario.", Toast.LENGTH_SHORT).show();
                    });
        } else if (!password.isEmpty()) {
            actualizarContraseña(password);
        } else {
            Toast.makeText(this, "No se realizaron cambios.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void actualizarContraseña(String password) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(password)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PerfilUsuarioActivity.this, "Contraseña actualizada con éxito.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PerfilUsuarioActivity.this, "Error al actualizar contraseña.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}