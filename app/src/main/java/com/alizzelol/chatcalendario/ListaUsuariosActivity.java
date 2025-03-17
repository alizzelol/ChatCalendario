package com.alizzelol.chatcalendario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alizzelol.chatcalendario.chat.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ListaUsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsuarios;
    private ListaUsuariosAdapter listaUsuariosAdapter;
    private List<User> listaUsuarios = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaUsuarios.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User usuario = new User(
                                    document.getString("username"),
                                    document.getString("nombre"),
                                    document.getString("apellido"),
                                    document.getString("email"),
                                    document.getString("telefono"),
                                    document.getString("rol"),
                                    document.getString("userId"),
                                    document.getId()
                            );
                            listaUsuarios.add(usuario);
                        }

                        listaUsuariosAdapter = new ListaUsuariosAdapter(listaUsuarios, userId -> {
                            Intent intent = new Intent(ListaUsuariosActivity.this, DetallesUsuarioActivity.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                        });
                        recyclerViewUsuarios.setAdapter(listaUsuariosAdapter);
                    } else {
                        Toast.makeText(this, "Error al cargar usuarios.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}