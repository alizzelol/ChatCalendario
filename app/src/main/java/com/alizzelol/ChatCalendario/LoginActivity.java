package com.alizzelol.ChatCalendario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alizzelol.chatcalendario.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etContraseña;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etContraseña = findViewById(R.id.etContraseña);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesionUsuario();
            }
        });
    }

    private void iniciarSesionUsuario() {
        String email = etEmail.getText().toString();
        String contraseña = etContraseña.getText().toString();

        mAuth.signInWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid(); // Obtener el uid de autenticación

                                db.collection("users").document(userId).get() // Usar el uid para buscar el documento
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful() && task.getResult().exists()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    Map<String, Object> userData = document.getData();
                                                    String username = (String) userData.get("username");
                                                    String rol = (String) userData.get("rol");

                                                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();

                                                    Intent intent;
                                                    if ("admin".equals(rol)) {
                                                        intent = new Intent(LoginActivity.this, CalendarioProfesor.class);
                                                    } else {
                                                        intent = new Intent(LoginActivity.this, CalendarioPadres.class);
                                                    }

                                                    intent.putExtra("username", username);
                                                    intent.putExtra("userId", userId); // Pasar el uid
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "Error al obtener los datos del usuario.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Error en el inicio de sesión: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}