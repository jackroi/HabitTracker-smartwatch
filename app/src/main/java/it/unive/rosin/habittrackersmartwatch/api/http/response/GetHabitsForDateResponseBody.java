package it.unive.rosin.habittrackersmartwatch.api.http.response;

import java.util.List;

import it.unive.rosin.habittrackersmartwatch.model.HabitType;
import it.unive.rosin.habittrackersmartwatch.model.HabitWithState;

public class GetHabitsForDateResponseBody extends ResponseBody {

    private List<HabitWithState> habits;

    public GetHabitsForDateResponseBody() { }

    public List<HabitWithState> getHabits() {
        return habits;
    }
}
