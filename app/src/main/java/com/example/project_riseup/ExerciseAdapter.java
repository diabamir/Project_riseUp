package com.example.project_riseup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exercises;
    private OnCheckedChangeListener listener;

    public interface OnCheckedChangeListener {
        void onCheckedChange(Exercise exercise, boolean isChecked);
    }

    public ExerciseAdapter(List<Exercise> exercises, OnCheckedChangeListener listener) {
        this.exercises = exercises;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.description.setText(exercise.getDescription());
        holder.type.setText("Type: " + exercise.getType());
        holder.level.setText("Level: " + exercise.getLevel());

        // Prevent triggering OnCheckedChangeListener during setChecked
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(exercise.isChecked());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onCheckedChange(exercise, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView description, type, level;
        CheckBox checkBox;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.exerciseDescription);
            type = itemView.findViewById(R.id.exerciseType);
            level = itemView.findViewById(R.id.exerciseLevel);
            checkBox = itemView.findViewById(R.id.exerciseCheckBox);
        }
    }
}
