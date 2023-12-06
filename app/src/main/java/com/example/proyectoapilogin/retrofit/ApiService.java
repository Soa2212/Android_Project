package com.example.proyectoapilogin.retrofit;

import com.example.proyectoapilogin.response.HabitacionDetalleResponse;
import com.example.proyectoapilogin.response.HabitacionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("habitacion")
    Call<HabitacionResponse> getHabitaciones();

    @GET("habitacion/{id}")
    Call<HabitacionDetalleResponse> getHabitacionById(@Path("id") int id);
}
