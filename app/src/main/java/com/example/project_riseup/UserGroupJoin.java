package com.example.project_riseup;

import androidx.room.Entity;

@Entity(tableName = "user_group_join", primaryKeys = {"userId", "groupId"})
public class UserGroupJoin {
    public long userId;
    public long groupId;

    // Status to indicate whether the user has requested, joined, or been declined
    public String status; // Values: "requested", "joined", "declined"

    public UserGroupJoin(long userId, long groupId, String status) {
        this.userId = userId;
        this.groupId = groupId;
        this.status = status;
    }

    // Getters and setters
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
//package com.example.project_riseup;
//
//import androidx.room.Entity;
//
//@Entity(tableName = "user_group_join", primaryKeys = {"userId", "groupId"})
//public class UserGroupJoin {
//    public long userId;
//    public long groupId;
//
//    // Status to indicate whether the user has requested, joined, or been declined
//    public String status; // Values: "requested", "joined", "declined"
//
//    public UserGroupJoin(long userId, long groupId, String status) {
//        this.userId = userId;
//        this.groupId = groupId;
//        this.status = status;
//    }
//
//    // Getters and setters
//    public long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }
//
//    public long getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(long groupId) {
//        this.groupId = groupId;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//}