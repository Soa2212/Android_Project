package com.example.proyectoapilogin.response;

import com.example.proyectoapilogin.model.Habitacion;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HabitacionDetalleResponse {
    @SerializedName("Habitación")
    private Habitacion habitacion;

    public Habitacion getHabitacion() {
        return habitacion;
    }

}
