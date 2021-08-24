package it.unive.rosin.habittrackersmartwatch.model;

public class HabitButton {

    private String id;
    private String name;
    private HabitState state;

    public HabitButton(String id, String name, HabitState state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void setState(HabitState state) {
        this.state = state;
    }

    public HabitState getState() {
        return state;
    }

    public HabitState nextState() {
        int nextStateOrdinal = (getState().ordinal() + 1) % HabitState.values().length;
        HabitState nextState = HabitState.values()[nextStateOrdinal];
        setState(nextState);
        return nextState;
    }

    public HabitState previousState() {
        int numberOfStates = HabitState.values().length;
        int previousStateOrdinal = (getState().ordinal() - 1 + numberOfStates) % numberOfStates;
        HabitState previousState = HabitState.values()[previousStateOrdinal];
        setState(previousState);
        return previousState;
    }
}
