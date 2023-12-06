package com.example.proyectoapilogin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectoapilogin.views.DetalleHabitacionActivity;
import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.model.Habitacion;

import java.util.List;

public class HabitacionAdapter extends RecyclerView.Adapter<HabitacionAdapter.ViewHolder> {
    private List<Habitacion> habitaciones;

    private Context context;

    // Constructor que acepta el contexto
    public HabitacionAdapter(Context context) {
        this.context = context;
    }
    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public HabitacionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habitacion, parent, false);
        return new HabitacionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitacionAdapter.ViewHolder holder, int position) {
        Habitacion habitacion = habitaciones.get(position);
        holder.nombreRoom.setText(String.valueOf(habitacion.getNombre()) + " #");
        holder.textViewId.setText(String.valueOf(habitacion.getId()));
        holder.detalleClickTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int habitacionId = habitacion.getId();
                abrirActividadDetalleHabitacion(habitacionId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return habitaciones != null ? habitaciones.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId,nombreRoom;
        ImageView detalleClickTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreRoom = itemView.findViewById(R.id.nombreRoom);
            textViewId = itemView.findViewById(R.id.textViewId);
            detalleClickTextView = itemView.findViewById(R.id.detalle_click);

        }
    }
    private void abrirActividadDetalleHabitacion(int habitacionId) {
        Intent intent = new Intent(context, DetalleHabitacionActivity.class);
        intent.putExtra("habitacionId", habitacionId);
        context.startActivity(intent);
    }
}
