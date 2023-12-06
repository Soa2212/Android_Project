package com.example.proyectoapilogin.response;
import com.example.proyectoapilogin.model.Habitacion;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HabitacionResponse {
    @SerializedName("Habitaciones")
    private List<Habitacion> habitaciones;

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }
}
