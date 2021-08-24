package it.unive.rosin.habittrackersmartwatch.api;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import it.unive.rosin.habittrackersmartwatch.api.http.request.CreateHabitHistoryEntryRequestBody;
import it.unive.rosin.habittrackersmartwatch.api.http.response.GetHabitsForDateResponseBody;
import it.unive.rosin.habittrackersmartwatch.api.http.response.ResponseBody;
import it.unive.rosin.habittrackersmartwatch.api.service.HabitService;
import it.unive.rosin.habittrackersmartwatch.model.HabitState;
import it.unive.rosin.habittrackersmartwatch.model.HistoryEntryType;
import retrofit2.Call;

public class HabitRepository {

    private HabitService habitService;

    public HabitRepository(HabitService habitService) {
        this.habitService = habitService;
    }

/*
    public Call<List<Habit>> getActiveHabits() {
        return habitService.getActiveHabits();
    }
*/

    public Call<GetHabitsForDateResponseBody> getHabitsForDate(String date) {
        return habitService.getHabitsForDate(date);
    }

    public Call<ResponseBody> setHabitAs(String habitId, OffsetDateTime date, HabitState state) {
        Objects.requireNonNull(date);
        Objects.requireNonNull(state);

        switch (state) {
            case COMPLETED:
                return habitService.setHabitAs(habitId, new CreateHabitHistoryEntryRequestBody(
                        date,
                        HistoryEntryType.COMPLETED
                ));

            case SKIPPED:
                return habitService.setHabitAs(habitId, new CreateHabitHistoryEntryRequestBody(
                        date,
                        HistoryEntryType.SKIPPED
                ));

            case NOT_COMPLETED:
                return habitService.setHabitAsNotCompleted(habitId, date.format(DateTimeFormatter.ISO_LOCAL_DATE));

            default:
                throw new AssertionError("Habit state not handled");
        }

    }

}
