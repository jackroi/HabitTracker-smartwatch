package it.unive.rosin.habittrackersmartwatch.api.http.request;

import it.unive.rosin.habittrackersmartwatch.model.HabitType;

public class CreateHabitRequestBody {
    private String name;
    private String category;
    private HabitType type;

    public CreateHabitRequestBody(String name, String category, HabitType type) {
        this.name = name;
        this.category = category;
        this.type = type;
    }
}
