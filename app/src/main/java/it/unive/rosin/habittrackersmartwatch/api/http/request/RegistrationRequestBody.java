package it.unive.rosin.habittrackersmartwatch.api.http.request;

public class RegistrationRequestBody {
    private String name;
    private String email;
    private String password;

    public RegistrationRequestBody(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
