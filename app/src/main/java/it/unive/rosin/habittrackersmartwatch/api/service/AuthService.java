package it.unive.rosin.habittrackersmartwatch.api.service;

import it.unive.rosin.habittrackersmartwatch.api.http.request.RegistrationRequestBody;
import it.unive.rosin.habittrackersmartwatch.api.http.response.LoginResponseBody;
import it.unive.rosin.habittrackersmartwatch.api.http.response.RegistrationResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AuthService {

    @GET("login")
    Call<LoginResponseBody> login(@Header("Authorization") String basicAuthenticationHeader);

    @GET("register")
    Call<RegistrationResponseBody> register(@Body RegistrationRequestBody body);

}
