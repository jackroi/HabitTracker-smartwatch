package it.unive.rosin.habittrackersmartwatch;

import java.net.URI;

import io.socket.client.IO;
import io.socket.client.Socket;

public class HabitSocketApplication {

    private static final String HABIT_SERVER_URL = "http://192.168.1.136:8000";

    private Socket socket;

    private static HabitSocketApplication instance;

    private HabitSocketApplication() {
        URI uri = URI.create(HABIT_SERVER_URL);
        IO.Options options = IO.Options.builder()
                .build();

        this.socket = IO.socket(uri, options);
    }

    public Socket getSocket() {
        return socket;
    }

    public static synchronized HabitSocketApplication getInstance() {
        if (instance == null) {
            instance = new HabitSocketApplication();
        }
        return instance;
    }
}
