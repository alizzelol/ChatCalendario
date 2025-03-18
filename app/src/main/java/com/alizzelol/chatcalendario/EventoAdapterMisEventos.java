package com.alizzelol.chatcalendario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class EventoAdapterMisEventos extends RecyclerView.Adapter<EventoAdapterMisEventos.EventoViewHolder> {

    private List<Evento> eventos;
    private OnEventoClickListener listener;

    public EventoAdapterMisEventos(List<Evento> eventos, OnEventoClickListener listener) {
        this.eventos = eventos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento_mis_eventos, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.tituloTextView.setText(evento.getTitulo());
        holder.descripcionTextView.setText(evento.getDescripcion());
        holder.horaTextView.setText(evento.getHora());
        holder.tipoTextView.setText(evento.getTipo());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.fechaTextView.setText(sdf.format(evento.getFecha()));
        holder.itemView.setOnClickListener(v -> listener.onEventoClick(evento));
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView;
        TextView descripcionTextView;
        TextView horaTextView;
        TextView tipoTextView;
        TextView fechaTextView;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.textViewTitulo);
            descripcionTextView = itemView.findViewById(R.id.textViewDescripcion);
            horaTextView = itemView.findViewById(R.id.textViewHora);
            tipoTextView = itemView.findViewById(R.id.textViewTipo);
            fechaTextView = itemView.findViewById(R.id.textViewFecha); // Inicializado TextView de la fecha
        }
    }

    public interface OnEventoClickListener {
        void onEventoClick(Evento evento);
    }
}