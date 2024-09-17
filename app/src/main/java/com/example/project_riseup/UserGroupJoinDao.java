package com.example.project_riseup;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserGroupJoinDao {

    // Insert a new UserGroupJoin relation
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserGroupJoin userGroupJoin);

    // Update the status of the user in the group
    @Update
    void update(UserGroupJoin userGroupJoin);

    // Get a UserGroupJoin relation by userId and groupId
    @Query("SELECT * FROM user_group_join WHERE userId = :userId AND groupId = :groupId LIMIT 1")
    LiveData<UserGroupJoin> getUserGroupJoin(long userId, long groupId);

    // Get all UserGroupJoin entries for a particular user
    @Query("SELECT * FROM user_group_join WHERE userId = :userId")
    LiveData<List<UserGroupJoin>> getUserGroups(long userId);

    // Delete a UserGroupJoin relation
    @Query("DELETE FROM user_group_join WHERE userId = :userId AND groupId = :groupId")
    void deleteUserGroupJoin(long userId, long groupId);




}
