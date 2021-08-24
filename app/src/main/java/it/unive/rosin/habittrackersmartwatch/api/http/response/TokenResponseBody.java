package it.unive.rosin.habittrackersmartwatch.api.http.response;

public abstract class TokenResponseBody extends ResponseBody {
    private String token;

    public TokenResponseBody() { }

    public String getToken() {
        return token;
    }
}
