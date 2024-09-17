package com.example.project_riseup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> meals;
    private MealClickListener listener;

    public MealAdapter(List<Meal> meals, MealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getMealName());
        holder.ingredients.setText(String.format("%s, %s, %s, %s", meal.getIngredient1(), meal.getIngredient2(), meal.getIngredient3(), meal.getIngredient4()));
        holder.checkBox.setChecked(meal.isChecked());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onMealClick(meal, isChecked));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName, ingredients;
        CheckBox checkBox;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealName);
            ingredients = itemView.findViewById(R.id.ingredients);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public interface MealClickListener {
        void onMealClick(Meal meal, boolean isChecked);
    }
}
