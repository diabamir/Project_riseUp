package com.example.project_riseup;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Date;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public long id;

    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthDate;
    private String password;
    private String gender;
    private double weight;
    private double height;
    private String fitnessLevel;
    private boolean seeTheInstructions;

    private String profileImageUri;  // Store the URI of the profile image

    @TypeConverters(Converters.class)
    private List<String> favActivities;
    @TypeConverters(Converters.class)
    private List<Workout> workoutList;
    @TypeConverters(Converters.class)
    private List<Nutrition> nutritionList;


    @Ignore
    public User(){

    }

    public User(long id, String userName, String firstName, String lastName, String phoneNumber, Date birthDate, String password, String gender, double weight, double height, String fitnessLevel, boolean seeTheInstructions, String profileImageUri, List<String> favActivities, List<Workout> workoutList, List<Nutrition> nutritionList) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.fitnessLevel = fitnessLevel;
        this.seeTheInstructions = seeTheInstructions;
        this.profileImageUri = profileImageUri;
        this.favActivities = favActivities;
        this.workoutList = workoutList;
        this.nutritionList = nutritionList;

    }

    public long getId() {
        return id;
    }

    public boolean getSeeTheInstructions() {
        return seeTheInstructions;
    }

    public void setSeeTheInstructions(boolean seeTheInstructions) {
        this.seeTheInstructions = seeTheInstructions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public List<String> getFavActivities() {
        return favActivities;
    }

    public void setFavActivities(List<String> favActivities) {
        this.favActivities = favActivities;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public List<Nutrition> getNutritionList() {
        return nutritionList;
    }

    public void setNutritionList(List<Nutrition> nutritionList) {
        this.nutritionList = nutritionList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", birthDate=" + birthDate +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", fitnessLevel='" + fitnessLevel + '\'' +
                ", profileImageUri='" + profileImageUri + '\'' +
                ", favActivities=" + favActivities +
                ", workoutList=" + workoutList +
                ", nutritionList=" + nutritionList +
                '}';
    }
}