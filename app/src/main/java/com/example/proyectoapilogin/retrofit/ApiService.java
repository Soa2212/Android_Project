package com.example.proyectoapilogin.retrofit;

import com.example.proyectoapilogin.model.TokenResponse;
import com.example.proyectoapilogin.response.HabitacionDetalleResponse;
import com.example.proyectoapilogin.response.HabitacionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiService {
    @GET("roomsUser")
    Call<HabitacionResponse> getHabitaciones();

    @GET("roomdetail/{id}")
    Call<HabitacionDetalleResponse> getHabitacionById(@Path("id") int id);

    @GET("validartoken")
    Call<TokenResponse> verificarToken();
}
