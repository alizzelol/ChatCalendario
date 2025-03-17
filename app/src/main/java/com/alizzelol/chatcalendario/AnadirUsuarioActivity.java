package com.alizzelol.chatcalendario;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnadirUsuarioActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextApellido, editTextEmail, editTextPassword, editTextPassword2, editTextTelefono, editTextRol;
    private Button buttonAñadir;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_usuario);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextRol = findViewById(R.id.editTextRol);
        buttonAñadir = findViewById(R.id.buttonAñadir);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        buttonAñadir.setOnClickListener(v -> añadirUsuario());
    }

    private void añadirUsuario() {
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String password2 = editTextPassword2.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String rol = editTextRol.getText().toString().toLowerCase();
        String username = nombre + " " + apellido;

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty() || telefono.isEmpty() || rol.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password2)) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String userId = UUID.randomUUID().toString();
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("username", username.toLowerCase());
                        userData.put("email", email);
                        userData.put("userId", userId);
                        userData.put("rol", rol);
                        userData.put("telefono", telefono);

                        db.collection("users").document(userId).set(userData)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AnadirUsuarioActivity.this, "Usuario añadido con éxito.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(AnadirUsuarioActivity.this, "Error al guardar los datos del usuario.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(AnadirUsuarioActivity.this, "Error al crear usuario.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}