package com.example.proyectoapilogin.retrofit;

import com.example.proyectoapilogin.model.Token;
import com.example.proyectoapilogin.model.User;
import com.example.proyectoapilogin.response.HabitacionDetalleResponse;
import com.example.proyectoapilogin.response.HabitacionResponse;
import com.example.proyectoapilogin.response.LoginResponse;
import com.example.proyectoapilogin.response.RegistroResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("roomsUser")
    Call<HabitacionResponse> getHabitaciones();

    @GET("roomdetail/{id}")
    Call<HabitacionDetalleResponse> getHabitacionById(@Path("id") int id);

    @GET("validartoken")
    Call<Token> verificarToken(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<RegistroResponse> register(
            @Field("name") String nombre,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String passwordconfirm);
}
