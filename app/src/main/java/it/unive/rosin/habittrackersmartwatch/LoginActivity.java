package it.unive.rosin.habittrackersmartwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import it.unive.rosin.habittrackersmartwatch.api.ApiClient;
import it.unive.rosin.habittrackersmartwatch.api.SessionManager;
import it.unive.rosin.habittrackersmartwatch.api.http.response.LoginResponseBody;
import it.unive.rosin.habittrackersmartwatch.api.service.AuthService;
import it.unive.rosin.habittrackersmartwatch.databinding.ActivityLoginBinding;
import it.unive.rosin.habittrackersmartwatch.databinding.ActivityMainBinding;
import it.unive.rosin.habittrackersmartwatch.model.HabitButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;

    private SessionManager sessionManager;
    private AuthService authService;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailEditText = binding.emailEditText;
        passwordEditText = binding.passwordEditText;
        loginButton = binding.loginButton;

        sessionManager = new SessionManager(this);
        ApiClient apiClient = new ApiClient(this);
        authService = apiClient.getAuthService();

        loginButton.setOnClickListener(v -> {
            Log.i(TAG, "Login button clicked");

            String email = validateEmail(emailEditText.getText().toString());
            String password = validatePassword(passwordEditText.getText().toString());

            if (email == null || password == null) {
                Log.i(TAG, "Invalid email or password field");
                // TODO maybe show Toast/SnackBar
                return;
            }

            String usernamePasswordBase64 = Base64.getEncoder().encodeToString(String.format("%s:%s", email, password).getBytes(StandardCharsets.UTF_8));
            String basicAuthenticationHeader = String.format("Basic %s", usernamePasswordBase64);

            authService.login(basicAuthenticationHeader).enqueue(new Callback<LoginResponseBody>() {
                @Override
                public void onResponse(Call<LoginResponseBody> call, Response<LoginResponseBody> response) {
                    Log.i(TAG, "Login response received");

                    if (response.isSuccessful()) {
                        Log.i(TAG, "Login successful");
                        String token = response.body().getToken();
                        sessionManager.saveAuthToken(token);
                        HabitSocketApplication app = HabitSocketApplication.getInstance();
                        app.getSocket().emit("online", token);
                        startActivity(new Intent(LoginActivity.this, HabitActivity.class));
                        finish();
                    }
                    else {
                        Log.w(TAG, "Login not successful");
                        // TODO maybe show Toast/SnackBar
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseBody> call, Throwable t) {
                    Log.w(TAG, "Login request failure: " + t.getMessage());
                }
            });
        });
    }

    @Nullable
    private static String validateEmail(@NonNull String email) {
        Objects.requireNonNull(email);

        String cleanedEmail = email.trim();
        if (cleanedEmail.length() == 0) {
            return null;
        }
        return cleanedEmail;
    }

    @Nullable
    private static String validatePassword(@NonNull String password) {
        Objects.requireNonNull(password);

        if (password.length() == 0) {
            return null;
        }
        return password;
    }

}
