package it.unive.rosin.habittrackersmartwatch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import it.unive.rosin.habittrackersmartwatch.api.ApiClient;
import it.unive.rosin.habittrackersmartwatch.api.HabitRepository;
import it.unive.rosin.habittrackersmartwatch.api.http.response.ResponseBody;
import it.unive.rosin.habittrackersmartwatch.databinding.HabitRowItemBinding;
import it.unive.rosin.habittrackersmartwatch.model.Habit;
import it.unive.rosin.habittrackersmartwatch.model.HabitButton;
import it.unive.rosin.habittrackersmartwatch.model.HabitState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitButtonAdapter extends RecyclerView.Adapter<HabitButtonAdapter.HabitButtonViewHolder> {
    public static final class HabitButtonViewHolder extends RecyclerView.ViewHolder {
        private HabitRowItemBinding binding;
        private TextView habitNameTextView;

        public HabitButtonViewHolder(@NonNull HabitRowItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            this.habitNameTextView = binding.habitNameTextView;
        }

    }

    private static final String TAG = "HabitButtonAdapter";
    private Context context;
    private List<HabitButton> habitButtonList;
    private HabitRepository habitRepository;

    public HabitButtonAdapter(Context context, List<HabitButton> habitButtonList, HabitRepository habitRepository) {
        this.context = context;
        this.habitButtonList = habitButtonList;
        this.habitRepository = habitRepository;
    }

    @NonNull
    @Override
    public HabitButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        HabitRowItemBinding habitRowItemBinding = HabitRowItemBinding.inflate(
                layoutInflater,
                parent,
                false
        );
        return new HabitButtonViewHolder(habitRowItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitButtonViewHolder holder, int position) {
        HabitButton habitButton = habitButtonList.get(position);
        holder.habitNameTextView.setText(habitButton.getName().toUpperCase(Locale.ROOT));
        holder.habitNameTextView.setBackground(context.getDrawable(getDrawableResourceFromState(habitButton.getState())));

        holder.itemView.setOnClickListener(v -> {
            Log.i(TAG, String.format("Habit clicked: %s", habitButton.getName()));

            habitButton.nextState();
            holder.habitNameTextView.setBackground(context.getDrawable(getDrawableResourceFromState(habitButton.getState())));
            Call<ResponseBody> call = habitRepository.setHabitAs(habitButton.getId(), OffsetDateTime.now(), habitButton.getState());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "Habit state updated successfully");
                    }
                    else {
                        Log.w(TAG, "Something wrong happened updating the habit state (onResponse)");
                        habitButton.previousState();
                        holder.habitNameTextView.setBackground(context.getDrawable(getDrawableResourceFromState(habitButton.getState())));
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.w(TAG, "Something wrong happened updating the habit state (onFailure)");
                    habitButton.previousState();
                    holder.habitNameTextView.setBackground(context.getDrawable(getDrawableResourceFromState(habitButton.getState())));
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return habitButtonList.size();
    }

    private static int getDrawableResourceFromState(HabitState habitState) {
        Objects.requireNonNull(habitState);

        switch (habitState) {
            case NOT_COMPLETED:
                return R.drawable.rounded_corner_notcompleted;
            case SKIPPED:
                return R.drawable.rounded_corner_skipped;
            case COMPLETED:
                return R.drawable.rounded_corner_completed;
            default:
                throw new AssertionError("HabitState '" + habitState + "' not handled");
        }
    }

    public void updateHabitButtonList(List<HabitButton> habitButtonList) {
        this.habitButtonList = habitButtonList;
        notifyDataSetChanged();
    }
}
