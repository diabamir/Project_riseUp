package com.example.project_riseup;

import androidx.room.TypeConverter;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public Long fromDate(Date date) {
        return date != null ? date.getTime() : null;
    }

    @TypeConverter
    public Date toDate(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }


    // Generic List<String> converter
    @TypeConverter
    public static List<String> stringToList(String json) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return json == null ? null : gson.fromJson(json, listType);
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        return list == null ? null : gson.toJson(list);
    }

    // List<Workout> converters
    @TypeConverter
    public static List<Workout> stringToWorkoutList(String json) {
        Type listType = new TypeToken<List<Workout>>() {}.getType();
        return json == null ? null : gson.fromJson(json, listType);
    }

    @TypeConverter
    public static String workoutListToString(List<Workout> list) {
        return list == null ? null : gson.toJson(list);
    }

    // List<Nutrition> converters
    @TypeConverter
    public static List<Nutrition> stringToNutritionList(String json) {
        Type listType = new TypeToken<List<Nutrition>>() {}.getType();
        return json == null ? null : gson.fromJson(json, listType);
    }

    @TypeConverter
    public static String nutritionListToString(List<Nutrition> list) {
        return list == null ? null : gson.toJson(list);
    }

    @TypeConverter
    public static String userToString(User user) {
        return gson.toJson(user);
    }

    @TypeConverter
    public static User stringToUser(String json) {
        return gson.fromJson(json, User.class);
    }

}
//package com.example.project_riseup;
//
//import androidx.room.TypeConverter;
//import java.util.Date;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.List;
//
//public class Converters {
//    private static final Gson gson = new Gson();
//
//    @TypeConverter
//    public Long fromDate(Date date) {
//        return date != null ? date.getTime() : null;
//    }
//
//    @TypeConverter
//    public Date toDate(Long timestamp) {
//        return timestamp != null ? new Date(timestamp) : null;
//    }
//
//
//    // Generic List<String> converter
//    @TypeConverter
//    public static List<String> stringToList(String json) {
//        Type listType = new TypeToken<List<String>>() {}.getType();
//        return json == null ? null : gson.fromJson(json, listType);
//    }
//
//    @TypeConverter
//    public static String listToString(List<String> list) {
//        return list == null ? null : gson.toJson(list);
//    }
//
//    // List<Workout> converters
//    @TypeConverter
//    public static List<Workout> stringToWorkoutList(String json) {
//        Type listType = new TypeToken<List<Workout>>() {}.getType();
//        return json == null ? null : gson.fromJson(json, listType);
//    }
//
//    @TypeConverter
//    public static String workoutListToString(List<Workout> list) {
//        return list == null ? null : gson.toJson(list);
//    }
//
//    // List<Nutrition> converters
//    @TypeConverter
//    public static List<Nutrition> stringToNutritionList(String json) {
//        Type listType = new TypeToken<List<Nutrition>>() {}.getType();
//        return json == null ? null : gson.fromJson(json, listType);
//    }
//
//    @TypeConverter
//    public static String nutritionListToString(List<Nutrition> list) {
//        return list == null ? null : gson.toJson(list);
//    }
//
//    @TypeConverter
//    public static String userToString(User user) {
//        return gson.toJson(user);
//    }
//
//    @TypeConverter
//    public static User stringToUser(String json) {
//        return gson.fromJson(json, User.class);
//    }
//
//}