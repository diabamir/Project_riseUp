package com.example.project_riseup;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Workouts")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String activityName;
    private String description;
    private int times;
    private int cals;
    private String level;
    private byte[] image; // Store the image as byte array

    public Workout(long id, String activityName, String description,
                   int times, int cals, String level, byte[] image) {
        this.id = id;
        this.activityName = activityName;
        this.description = description;
        this.times = times;
        this.cals = cals;
        this.level = level;
        this.image = image; // Directly store the byte array
    }

    public Workout() {
        // Room uses this empty constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getCals() {
        return cals;
    }

    public void setCals(int cals) {
        this.cals = cals;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", description='" + description + '\'' +
                ", times=" + times +
                ", cals=" + cals +
                ", level='" + level + '\'' +
                ", image=" + (image != null ? "Image Size: " + image.length + " bytes" : "No Image") +
                '}';
    }
}
//package com.example.project_riseup;
//
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//@Entity(tableName = "Workouts")
//public class Workout {
//    @PrimaryKey(autoGenerate = true)
//    private long id;
//    private String activityName;
//    private String description;
//    private int times;
//    private int cals;
//    private String level;
//    private byte[] image; // Store the image as byte array
//
//    public Workout(long id, String activityName, String description,
//                   int times, int cals, String level, byte[] image) {
//        this.id = id;
//        this.activityName = activityName;
//        this.description = description;
//        this.times = times;
//        this.cals = cals;
//        this.level = level;
//        this.image = image; // Directly store the byte array
//    }
//
//    public Workout() {
//        // Room uses this empty constructor
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getActivityName() {
//        return activityName;
//    }
//
//    public void setActivityName(String activityName) {
//        this.activityName = activityName;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public int getTimes() {
//        return times;
//    }
//
//    public void setTimes(int times) {
//        this.times = times;
//    }
//
//    public int getCals() {
//        return cals;
//    }
//
//    public void setCals(int cals) {
//        this.cals = cals;
//    }
//
//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//
//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }
//
//    @Override
//    public String toString() {
//        return "Workout{" +
//                "id=" + id +
//                ", activityName='" + activityName + '\'' +
//                ", description='" + description + '\'' +
//                ", times=" + times +
//                ", cals=" + cals +
//                ", level='" + level + '\'' +
//                ", image=" + (image != null ? "Image Size: " + image.length + " bytes" : "No Image") +
//                '}';
//    }
//}
