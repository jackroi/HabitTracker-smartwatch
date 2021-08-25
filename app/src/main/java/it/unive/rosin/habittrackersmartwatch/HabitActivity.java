package it.unive.rosin.habittrackersmartwatch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import it.unive.rosin.habittrackersmartwatch.api.ApiClient;
import it.unive.rosin.habittrackersmartwatch.api.HabitRepository;
import it.unive.rosin.habittrackersmartwatch.api.http.response.GetHabitsForDateResponseBody;
import it.unive.rosin.habittrackersmartwatch.api.service.HabitService;
import it.unive.rosin.habittrackersmartwatch.databinding.ActivityHabitBinding;
import it.unive.rosin.habittrackersmartwatch.model.HabitButton;
import it.unive.rosin.habittrackersmartwatch.model.HabitWithState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitActivity extends Activity {

    private static final String TAG = "HabitActivity";

    private ActivityHabitBinding binding;

    private HabitViewModel habitViewModel;
    private HabitRepository habitRepository;

    private RecyclerView habitsRecyclerView;
    private HabitButtonAdapter habitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHabitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HabitService habitService = new ApiClient(this).getHabitService();
        this.habitRepository = new HabitRepository(habitService);

        setupHabitRecyclerView();

        fetchHabits();

        HabitSocketApplication app = HabitSocketApplication.getInstance();
        Socket socket = app.getSocket();

        Emitter.Listener listener = args -> fetchHabits();

        socket.on("habitCreated", listener);
        socket.on("habitUpdated", listener);
        socket.on("habitHistoryUpdated", listener);
        socket.on("habitDeleted", listener);

/*
        habitViewModel = new ViewModelProvider(this).get(HabitViewModel.class);
        habitViewModel.getHabits().observe(this, new Observer<List<HabitWithState>>() {
            @Override
            public void onChanged(List<HabitWithState> habitWithStates) {
                List<HabitButton> habitButtonList = habitWithStates.stream()
                        .map(habitWithState -> new HabitButton(habitWithState.getName(), habitWithState.getState()))
                        .collect(Collectors.toList());
                setupMenuRecyclerView();
            }
        });
*/
    }

    private void setupHabitRecyclerView() {
        habitsRecyclerView = binding.habitsRecyclerView;
        RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
        );
        habitsRecyclerView.setLayoutManager(recyclerViewLayoutManager);

        habitAdapter = new HabitButtonAdapter(this, new ArrayList<>(), habitRepository);
        habitsRecyclerView.setAdapter(habitAdapter);
    }

    private void fetchHabits() {
        Log.i(TAG, "Fetching habits...");

        LocalDate today = LocalDate.now();
        String dateString = today.format(DateTimeFormatter.ISO_DATE);

        Call<GetHabitsForDateResponseBody> habitListCall = habitRepository.getHabitsForDate(dateString);
        habitListCall.enqueue(new Callback<GetHabitsForDateResponseBody>() {
            @Override
            public void onResponse(Call<GetHabitsForDateResponseBody> call, Response<GetHabitsForDateResponseBody> response) {
                if (response.isSuccessful()) {
                    List<HabitWithState> habitWithStates = response.body().getHabits();
                    List<HabitButton> habitButtonList = habitWithStates.stream()
                            .map(habitWithState -> new HabitButton(habitWithState.getId(), habitWithState.getName(), habitWithState.getState()))
                            .collect(Collectors.toList());

                    habitAdapter.updateHabitButtonList(habitButtonList);
                }
            }

            @Override
            public void onFailure(Call<GetHabitsForDateResponseBody> call, Throwable t) {
                // show error message to user
            }
        });
    }

}
