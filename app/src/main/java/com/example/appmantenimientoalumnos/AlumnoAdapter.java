package com.example.appmantenimientoalumnos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.ViewHolder> {

    private List<Alumnos> listaAlumnos;
    private MainActivity context;

    public AlumnoAdapter(List<Alumnos> listaAlumnos, MainActivity context) {
        this.listaAlumnos = listaAlumnos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alumno, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alumnos alumno = listaAlumnos.get(position);

        // Se usan los nuevos métodos corregidos
        holder.tvCodigo.setText("DNI: " + alumno.getDni());
        holder.tvNombreCompleto.setText(alumno.getNombre());
        holder.tvEmail.setText(alumno.getCorreo());

        holder.btnEliminar.setOnClickListener(v -> context.confirmarEliminacion(alumno));
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodigo, tvNombreCompleto, tvEmail;
        ImageButton btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}