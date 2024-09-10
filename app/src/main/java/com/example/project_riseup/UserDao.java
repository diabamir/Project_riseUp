package com.example.project_riseup;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();
    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);
    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users WHERE firstName = :firstName AND lastName = :lastName")
    User getUserByUsername(String firstName, String lastName);

    @Query("SELECT * FROM users WHERE phoneNumber = :phoneNumber")
    User getUserByPhoneNumber(String phoneNumber);

    @Query("SELECT * FROM users WHERE password = :password")
    User getUserByPassword(String password);

    @Query("SELECT * FROM users WHERE firstName = :firstName")
    User getUserByFirstName(String firstName);

    @Query("SELECT * FROM users WHERE lastName = :lastName")
    User getUserByLastName(String lastName);

    @Query("SELECT * FROM users WHERE gender = :gender")
    User getUserByGender(String gender);



    @Query("SELECT * FROM users WHERE fitnessLevel = :fitnessLevel")
    User getUserByFitnessLevel(String fitnessLevel);

    @Query("SELECT * FROM users WHERE weight = :weight")
    User getUserByWeight(String weight);

    @Query("SELECT * FROM users WHERE height = :height")
    User getUserByHeight(String height);


//    @Query("SELECT * FROM users WHERE workoutList = :workoutList")
//    User getUserByWorkoutList(String workoutList);

}
