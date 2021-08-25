package it.unive.rosin.habittrackersmartwatch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import it.unive.rosin.habittrackersmartwatch.api.ApiClient;
import it.unive.rosin.habittrackersmartwatch.api.HabitRepository;
import it.unive.rosin.habittrackersmartwatch.api.http.response.GetHabitsForDateResponseBody;
import it.unive.rosin.habittrackersmartwatch.api.service.HabitService;
import it.unive.rosin.habittrackersmartwatch.model.Habit;
import it.unive.rosin.habittrackersmartwatch.model.HabitWithState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitViewModel extends ViewModel {

    private final MutableLiveData<List<HabitWithState>> _habitList = new MutableLiveData<>(new ArrayList<>());

    public HabitViewModel(HabitRepository habitRepository) {
        LocalDate today = LocalDate.now();
        String dateString = today.format(DateTimeFormatter.ISO_DATE);

        Call<GetHabitsForDateResponseBody> habitListCall = habitRepository.getHabitsForDate(dateString);
        habitListCall.enqueue(new Callback<GetHabitsForDateResponseBody>() {
            @Override
            public void onResponse(Call<GetHabitsForDateResponseBody> call, Response<GetHabitsForDateResponseBody> response) {
                if (response.isSuccessful()) {
                    List<HabitWithState> habitList = response.body().getHabits();
                    _habitList.setValue(habitList);
                }
            }

            @Override
            public void onFailure(Call<GetHabitsForDateResponseBody> call, Throwable t) {
                // show error message to user
            }
        });
    }

    public LiveData<List<HabitWithState>> getHabits() {
        return _habitList;
    }

}
