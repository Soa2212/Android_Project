package com.example.proyectoapilogin.retrofit;

import static com.example.proyectoapilogin.constants.AppConstant.BASE_URL;



import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.proyectoapilogin.constants.AppConstant;

public class RetrofitRequest {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            String authToken = getAuthTokenFromSharedPreferences(context);

            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", "Bearer " + authToken)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }

    private static String getAuthTokenFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }
}

