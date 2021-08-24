package it.unive.rosin.habittrackersmartwatch.api;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private SessionManager sessionManager;

    public AuthInterceptor(Context context) {
        sessionManager = new SessionManager(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        String token = sessionManager.fetchAuthToken();
        if (token != null) {
            requestBuilder.addHeader("Authorization", String.format("Bearer %s", token));
        }

        return chain.proceed(requestBuilder.build());
    }
}
