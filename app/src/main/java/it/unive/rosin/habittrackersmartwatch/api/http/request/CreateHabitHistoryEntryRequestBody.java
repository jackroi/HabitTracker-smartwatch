package it.unive.rosin.habittrackersmartwatch.api.http.request;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import it.unive.rosin.habittrackersmartwatch.model.HistoryEntryType;

public class CreateHabitHistoryEntryRequestBody {
    private String date;
    private String type;

    public CreateHabitHistoryEntryRequestBody(OffsetDateTime date, HistoryEntryType historyEntryType) {
        this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX':00'"));
        this.type = historyEntryType.name();
    }
}
