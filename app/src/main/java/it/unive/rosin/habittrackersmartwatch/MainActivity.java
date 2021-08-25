package it.unive.rosin.habittrackersmartwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import it.unive.rosin.habittrackersmartwatch.api.SessionManager;
import it.unive.rosin.habittrackersmartwatch.databinding.ActivityMainBinding;
import it.unive.rosin.habittrackersmartwatch.model.HabitButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.fetchAuthToken();

        Class<?> activityToStart;
        if (token == null) {            // User is non authenticated
            // Show login activity
            activityToStart = LoginActivity.class;
        }
        else {                          // User is authenticated
            // Show habit activity
            activityToStart = HabitActivity.class;
            HabitSocketApplication app = HabitSocketApplication.getInstance();
            Socket socket = app.getSocket();
            socket.connect();
            socket.emit("online", token);
        }
        // Start the activity
        startActivity(new Intent(this, activityToStart));
        finish();
    }

}
