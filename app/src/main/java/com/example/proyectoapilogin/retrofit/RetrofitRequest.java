package com.example.proyectoapilogin.retrofit;

import static com.example.proyectoapilogin.constants.AppConstant.BASE_URL;
import static com.example.proyectoapilogin.constants.AppConstant.authToken;



import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {
    private static Retrofit retrofit;


    public static void setAuthToken(String token) {
        retrofit = null;
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (authToken != null && !authToken.isEmpty()) {
                httpClient.addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + authToken)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                });
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
