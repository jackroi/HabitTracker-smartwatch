package it.unive.rosin.habittrackersmartwatch.api;

import android.content.Context;
import android.content.SharedPreferences;

import it.unive.rosin.habittrackersmartwatch.R;

public class SessionManager {

    private static final String USER_TOKEN_KEY = "USER_TOKEN";

    private final SharedPreferences sharedPreferences;

    public SessionManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.app_name),
                Context.MODE_PRIVATE
        );
    }

    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN_KEY, token);
        editor.apply();
    }

    public String fetchAuthToken() {
        return sharedPreferences.getString(USER_TOKEN_KEY, null);
    }

}
