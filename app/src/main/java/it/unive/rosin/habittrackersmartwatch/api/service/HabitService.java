package it.unive.rosin.habittrackersmartwatch.api.service;

import java.util.List;

import it.unive.rosin.habittrackersmartwatch.api.http.request.CreateHabitRequestBody;
import it.unive.rosin.habittrackersmartwatch.api.http.request.CreateHabitHistoryEntryRequestBody;
import it.unive.rosin.habittrackersmartwatch.api.http.response.GetHabitsForDateResponseBody;
import it.unive.rosin.habittrackersmartwatch.api.http.response.ResponseBody;
import it.unive.rosin.habittrackersmartwatch.model.Habit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HabitService {

///////////////////////////////////////////////////////////////////////////

    // TODO fix types
    @GET("habits")
    Call<List<Habit>> getActiveHabits();

    @GET("user/{habit_id}")
    Call<Habit> getHabitById(@Path("habit_id") String id);

    @POST("habits")
    Call<Habit> createHabit(@Body CreateHabitRequestBody body);

///////////////////////////////////////////////////////////////////////////

    @GET("habits")
    Call<GetHabitsForDateResponseBody> getHabitsForDate(@Query("date") String date);

    @POST("habits/{habit_id}/history")
    Call<ResponseBody> setHabitAs(@Path("habit_id") String id, @Body CreateHabitHistoryEntryRequestBody body);

    @DELETE("habits/{habit_id}/history/{date}")
    Call<ResponseBody> setHabitAsNotCompleted(@Path("habit_id") String id, @Path("date") String date);

}
