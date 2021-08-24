package it.unive.rosin.habittrackersmartwatch.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Habit {
    private String id;
    private String name;
    private String creationDate;
    private String category;
    private HabitType type;
    private boolean archived;

    public Habit() { }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        Date date;
        try {
            date = DateFormat.getDateInstance().parse(creationDate);
        } catch (ParseException e) {
            date = new Date();
            e.printStackTrace();
        }
        return date;
    }

    public String getCategory() {
        return category;
    }

    public HabitType getType() {
        return type;
    }

    public boolean isArchived() {
        return archived;
    }
}
