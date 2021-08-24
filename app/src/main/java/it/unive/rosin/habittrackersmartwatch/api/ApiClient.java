package it.unive.rosin.habittrackersmartwatch.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import it.unive.rosin.habittrackersmartwatch.api.service.AuthService;
import it.unive.rosin.habittrackersmartwatch.api.service.HabitService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    @NonNull
    private static final String BASE_URL = "http://192.168.1.136:8000/v0.0.1/";

    @NonNull
    private Retrofit retrofit;

    @Nullable
    private AuthService authService;

    @Nullable
    private HabitService habitService;

    public ApiClient(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient(context))
                .build();
    }

    @NonNull
    public synchronized AuthService getAuthService() {
        if (authService == null) {
            authService = retrofit.create(AuthService.class);
        }
        return authService;
    }

    @NonNull
    public synchronized HabitService getHabitService() {
        if (habitService == null) {
            habitService = retrofit.create(HabitService.class);
        }
        return habitService;
    }

    @NonNull
    private OkHttpClient okHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .build();
    }
}
