package com.example.project_riseup;

//package com.example.project_riseup;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface groupDAO {

    @Insert
    void insertGroup(Group group);

    @Update
    void updateGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Query("SELECT * FROM groups WHERE id = :groupId")
    LiveData<Group> getGroupById(long groupId);

    @Query("SELECT * FROM groups")
    LiveData<List<Group>> getAllGroups();

    @Query("DELETE FROM groups")
    void deleteAllGroups();

    @Query("SELECT * FROM groups WHERE location = :location")
    LiveData<List<Group>> getGroupsByLocation(String location);


}
//package com.example.project_riseup;
//
////package com.example.project_riseup;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import java.util.List;
//
//@Dao
//public interface groupDAO {
//
//    @Insert
//    void insertGroup(Group group);
//
//    @Update
//    void updateGroup(Group group);
//
//    @Delete
//    void deleteGroup(Group group);
//
//    @Query("SELECT * FROM groups WHERE id = :groupId")
//    LiveData<Group> getGroupById(long groupId);
//
//    @Query("SELECT * FROM groups")
//    LiveData<List<Group>> getAllGroups();
//
//    @Query("DELETE FROM groups")
//    void deleteAllGroups();
//
//    @Query("SELECT * FROM groups WHERE location = :location")
//    LiveData<List<Group>> getGroupsByLocation(String location);
//
//
//}
