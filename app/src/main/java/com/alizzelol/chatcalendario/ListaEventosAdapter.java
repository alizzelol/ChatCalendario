package com.alizzelol.chatcalendario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListaEventosAdapter extends RecyclerView.Adapter<ListaEventosAdapter.ListaEventosViewHolder> {

    private List<Evento> listaEventos;
    private OnListaEventoClickListener listener;

    public interface OnListaEventoClickListener {
        void onEventoClick(String eventoId);
    }

    public ListaEventosAdapter(List<Evento> listaEventos, OnListaEventoClickListener listener) {
        this.listaEventos = listaEventos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListaEventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento_lista, parent, false);
        return new ListaEventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaEventosViewHolder holder, int position) {
        Evento evento = listaEventos.get(position);
        holder.textViewTitulo.setText(evento.getTitulo());
        holder.textViewDescripcion.setText(evento.getDescripcion());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.textViewFecha.setText(sdf.format(evento.getFecha()));
        holder.textViewHora.setText(evento.getHora());
        holder.textViewTipo.setText(evento.getTipo());
        holder.itemView.setOnClickListener(v -> listener.onEventoClick(evento.getId()));
    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    public static class ListaEventosViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo, textViewDescripcion, textViewFecha, textViewHora, textViewTipo;

        public ListaEventosViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewHora = itemView.findViewById(R.id.textViewHora);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            textViewTipo = itemView.findViewById(R.id.textViewTipo);
        }
    }
}
