package com.example.project_riseup;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.os.Handler;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExerciseActivity extends AppCompatActivity {

    private AppDatabase db;
    private List<Exercise> exercises = new ArrayList<>();
    private ExerciseAdapter adapter;
    private RecyclerView recyclerView;

    // Define a background thread pool
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler mainThreadHandler = new Handler(); // For API 21 compatibility

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use the correct layout file
        setContentView(R.layout.activity_excercise);  // Updated layout name

        // Initialize Room database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "exercise-db").build();

        // Set up RecyclerView
        recyclerView = findViewById(R.id.exerciseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up Submit button
        Button submitButton = findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(v -> {
            executorService.execute(() -> {
                resetAllCheckboxes(); // Reset checked status
                List<Exercise> recommendedExercises = recommendExercises();
                mainThreadHandler.post(() -> {
                    exercises.clear();
                    exercises.addAll(recommendedExercises);
                    adapter.notifyDataSetChanged();
                });
            });
        });

        // Load exercises in the background
        executorService.execute(() -> {
            resetDatabase(); // Reset database and reload exercises
            exercises = db.exerciseDao().getAllExercises();
            mainThreadHandler.post(() -> {
                adapter = new ExerciseAdapter(exercises, (exercise, isChecked) -> {
                    executorService.execute(() -> {
                        exercise.setChecked(isChecked);
                        db.exerciseDao().insertExercise(exercise);
                    });
                });
                recyclerView.setAdapter(adapter);

                // Show random exercises on app startup
                List<Exercise> randomExercises = getRandomExercises(exercises, 5, new HashSet<>());
                exercises.clear();
                exercises.addAll(randomExercises);
                adapter.notifyDataSetChanged();
            });
        });
    }

    // Reset all exercises in the database
    private void resetDatabase() {
        db.exerciseDao().deleteAllExercises(); // Delete all old exercises
        loadExercisesFromCSV(); // Reload exercises from the updated CSV
    }

    // Load exercises from a CSV file
    private void loadExercisesFromCSV() {
        // Check if database is empty
        if (db.exerciseDao().getAllExercises().isEmpty()) {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.exercises); // Ensure exercises.csv is in res/raw folder
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] columns = line.split(",", -1); // Include empty strings
                    if (columns.length == 4) {
                        String description = columns[1].replace("\"", "");

                        // Skip unwanted exercises
                        if (description.equals("Running") || description.equals("Jumping Jacks") || description.equals("Bicycle Crunches")) {
                            continue;
                        }

                        Exercise exercise = new Exercise();
                        exercise.setId(Integer.parseInt(columns[0]));
                        exercise.setDescription(description);
                        exercise.setType(columns[2]);
                        exercise.setLevel(columns[3]);
                        db.exerciseDao().insertExercise(exercise);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Recommend exercises based on user preferences
    private List<Exercise> recommendExercises() {
        List<Exercise> likedExercises = db.exerciseDao().getLikedExercises();
        List<Exercise> allExercises = db.exerciseDao().getAllExercises();

        // Filter out excluded exercises
        List<String> excludedExercises = List.of("Running", "Jumping Jacks", "Bicycle Crunches");
        List<Exercise> filteredExercises = new ArrayList<>();
        for (Exercise exercise : allExercises) {
            if (!excludedExercises.contains(exercise.getDescription())) {
                filteredExercises.add(exercise);
            }
        }

        if (likedExercises.isEmpty()) {
            // Return random exercises if no likes yet
            return getRandomExercises(filteredExercises, 5, new HashSet<>());
        } else {
            // Use AI to recommend exercises based on type only
            return getAIRecommendations(likedExercises, filteredExercises, 5, new HashSet<>());
        }
    }

    // Get random exercises from the list
    private List<Exercise> getRandomExercises(List<Exercise> exercises, int count, HashSet<Integer> usedIndices) {
        List<Exercise> randomExercises = new ArrayList<>();
        Random random = new Random();
        int size = exercises.size();

        while (randomExercises.size() < count && usedIndices.size() < size) {
            int index = random.nextInt(size);
            if (!usedIndices.contains(index)) {
                randomExercises.add(exercises.get(index));
                usedIndices.add(index);
            }
        }
        return randomExercises;
    }

    // Recommend exercises using a simple AI based on similarity
    private List<Exercise> getAIRecommendations(List<Exercise> likedExercises, List<Exercise> allExercises, int count, HashSet<Integer> usedIndices) {
        double[] userPreferences = getUserPreferenceVector(likedExercises);
        List<ExerciseSimilarity> similarities = new ArrayList<>();

        for (Exercise exercise : allExercises) {
            if (!exercise.isChecked()) {
                double similarity = cosineSimilarity(userPreferences, getFeatureVector(exercise));
                similarities.add(new ExerciseSimilarity(exercise, similarity));
            }
        }

        // Sort exercises by similarity
        Collections.sort(similarities, (a, b) -> Double.compare(b.similarity, a.similarity));

        // Select top exercises based on similarity
        List<Exercise> recommendations = new ArrayList<>();
        Random random = new Random();
        while (recommendations.size() < count && !similarities.isEmpty()) {
            int randomIndex = random.nextInt(Math.min(similarities.size(), 5)); // Pick from top 5
            Exercise selectedExercise = similarities.get(randomIndex).exercise;

            if (!usedIndices.contains(selectedExercise.getId())) {
                recommendations.add(selectedExercise);
                usedIndices.add(selectedExercise.getId());
                similarities.remove(randomIndex); // Remove to avoid duplicates
            }
        }

        return recommendations;
    }

    // This function considers only the "Type" of exercise
    private double[] getFeatureVector(Exercise exercise) {
        double[] vector = new double[3]; // Only consider "Type"

        // Types: Strength, Cardio, Flexibility
        switch (exercise.getType()) {
            case "Strength":
                vector[0] = 1;
                break;
            case "Cardio":
                vector[1] = 1;
                break;
            case "Flexibility":
                vector[2] = 1;
                break;
        }

        return vector;
    }

    // Create a user preference vector based on liked exercises' "Type"
    private double[] getUserPreferenceVector(List<Exercise> likedExercises) {
        double[] preference = new double[3];
        for (Exercise exercise : likedExercises) {
            double[] vector = getFeatureVector(exercise);
            for (int i = 0; i < preference.length; i++) {
                preference[i] += vector[i];
            }
        }

        // Normalize the preference vector
        double magnitude = 0;
        for (double val : preference) {
            magnitude += val * val;
        }
        magnitude = Math.sqrt(magnitude);
        if (magnitude > 0) {
            for (int i = 0; i < preference.length; i++) {
                preference[i] /= magnitude;
            }
        }

        return preference;
    }

    // Calculate cosine similarity between vectors
    private double cosineSimilarity(double[] vecA, double[] vecB) {
        double dotProduct = 0;
        double magnitudeA = 0;
        double magnitudeB = 0;
        for (int i = 0; i < vecA.length; i++) {
            dotProduct += vecA[i] * vecB[i];
            magnitudeA += vecA[i] * vecA[i];
            magnitudeB += vecB[i] * vecB[i];
        }
        magnitudeA = Math.sqrt(magnitudeA);
        magnitudeB = Math.sqrt(magnitudeB);
        if (magnitudeA == 0 || magnitudeB == 0) {
            return 0;
        }
        return dotProduct / (magnitudeA * magnitudeB);
    }

    // Reset all exercise checkboxes
    private void resetAllCheckboxes() {
        List<Exercise> allExercises = db.exerciseDao().getAllExercises();
        for (Exercise exercise : allExercises) {
            if (exercise.isChecked()) {
                exercise.setChecked(false); // Reset the checked status
                db.exerciseDao().insertExercise(exercise); // Update the database
            }
        }
    }

    // Class for exercise similarity comparison
    private static class ExerciseSimilarity {
        Exercise exercise;
        double similarity;

        ExerciseSimilarity(Exercise exercise, double similarity) {
            this.exercise = exercise;
            this.similarity = similarity;
        }
    }
}
