package com.alizzelol.ChatCalendario.profesor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.alizzelol.ChatCalendario.chat.User;
import com.alizzelol.chatcalendario.R;

import java.util.List;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.UserViewHolder> {
    private List<User> listaUsuarios;
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(String userId);
    }

    public ListaUsuariosAdapter(List<User> listaUsuarios, OnUserClickListener listener) {
        this.listaUsuarios = listaUsuarios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User usuario = listaUsuarios.get(position);
        holder.textViewNombre.setText(usuario.getNombre());
        holder.textViewApellido.setText(usuario.getApellido());
        holder.textViewEmail.setText(usuario.getEmail());
        holder.textViewTelefono.setText(usuario.getTelefono());
        holder.textViewRol.setText(usuario.getRol());
        holder.itemView.setOnClickListener(v -> listener.onUserClick(usuario.getUserId()));
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        TextView textViewApellido;
        TextView textViewEmail;
        TextView textViewTelefono;
        TextView textViewRol;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewApellido = itemView.findViewById(R.id.textViewApellido);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewTelefono = itemView.findViewById(R.id.textViewTelefono);
            textViewRol = itemView.findViewById(R.id.textViewRol);
        }
    }
}
