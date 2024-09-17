package com.example.project_riseup;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "groups")
public class Group {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String workOut;
    public String location;
    public String discribtion;
    public String startTime;
    public String endTime;
    public Date date;
    public int userAdminId;
    public User users;
    public int membersNumber;
    public int howManyJoin;
    public int imageGroup;  // Changed to int because the in=mage is already in drowable


    public Group(long id, String workOut, String location, String discribtion,
                 String startTime, String endTime, Date date, int userAdminId, User users,
                 int membersNumber, int howManyJoin, int imageGroup) {
        this.id = id;
        this.workOut = workOut;
        this.location = location;
        this.discribtion = discribtion;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.userAdminId = userAdminId;
        this.users = users;
        this.membersNumber = membersNumber;
        this.howManyJoin = howManyJoin;
        this.imageGroup = imageGroup;  }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWorkOut() {
        return workOut;
    }

    public void setWorkOut(String workOut) {
        this.workOut = workOut;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDiscribtion() {
        return discribtion;
    }

    public void setDiscribtion(String discribtion) {
        this.discribtion = discribtion;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserAdminId() {
        return userAdminId;
    }

    public void setUserAdminId(int userAdminId) {
        this.userAdminId = userAdminId;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public int getMembersNumber() {
        return membersNumber;
    }

    public void setMembersNumber(int membersNumber) {
        this.membersNumber = membersNumber;
    }

    public int getHowManyJoin() {
        return howManyJoin;
    }

    public void setHowManyJoin(int howManyJoin) {
        this.howManyJoin = howManyJoin;
    }

    public int getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(int imageGroup) {
        this.imageGroup = imageGroup;
    }

    @Override
    public String toString() {
        return "group{" +
                "id=" + id +
                ", workOut='" + workOut + '\'' +
                ", location='" + location + '\'' +
                ", discribtion='" + discribtion + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                ", userAdminId=" + userAdminId +
                ", users=" + users +
                ", membersNumber=" + membersNumber +
                ", howManyJoin=" + howManyJoin +
                ", imageGroup=" + imageGroup +
                '}';
    }
}
//package com.example.project_riseup;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import java.util.Date;
//
//@Entity(tableName = "groups")
//public class Group {
//
//    @PrimaryKey(autoGenerate = true)
//    public long id;
//    public String workOut;
//    public String location;
//    public String discribtion;
//    public String startTime;
//    public String endTime;
//    public Date date;
//    public int userAdminId;
//    public User users;
//    public int membersNumber;
//    public int howManyJoin;
//    public int imageGroup;  // Changed to int because the in=mage is already in drowable
//
//
//    public Group(long id, String workOut, String location, String discribtion,
//                 String startTime, String endTime, Date date, int userAdminId, User users,
//                 int membersNumber, int howManyJoin, int imageGroup) {
//        this.id = id;
//        this.workOut = workOut;
//        this.location = location;
//        this.discribtion = discribtion;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.date = date;
//        this.userAdminId = userAdminId;
//        this.users = users;
//        this.membersNumber = membersNumber;
//        this.howManyJoin = howManyJoin;
//        this.imageGroup = imageGroup;  }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getWorkOut() {
//        return workOut;
//    }
//
//    public void setWorkOut(String workOut) {
//        this.workOut = workOut;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getDiscribtion() {
//        return discribtion;
//    }
//
//    public void setDiscribtion(String discribtion) {
//        this.discribtion = discribtion;
//    }
//
//    public String getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }
//
//    public String getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = endTime;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public int getUserAdminId() {
//        return userAdminId;
//    }
//
//    public void setUserAdminId(int userAdminId) {
//        this.userAdminId = userAdminId;
//    }
//
//    public User getUsers() {
//        return users;
//    }
//
//    public void setUsers(User users) {
//        this.users = users;
//    }
//
//    public int getMembersNumber() {
//        return membersNumber;
//    }
//
//    public void setMembersNumber(int membersNumber) {
//        this.membersNumber = membersNumber;
//    }
//
//    public int getHowManyJoin() {
//        return howManyJoin;
//    }
//
//    public void setHowManyJoin(int howManyJoin) {
//        this.howManyJoin = howManyJoin;
//    }
//
//    public int getImageGroup() {
//        return imageGroup;
//    }
//
//    public void setImageGroup(int imageGroup) {
//        this.imageGroup = imageGroup;
//    }
//
//    @Override
//    public String toString() {
//        return "group{" +
//                "id=" + id +
//                ", workOut='" + workOut + '\'' +
//                ", location='" + location + '\'' +
//                ", discribtion='" + discribtion + '\'' +
//                ", startTime=" + startTime +
//                ", endTime=" + endTime +
//                ", date=" + date +
//                ", userAdminId=" + userAdminId +
//                ", users=" + users +
//                ", membersNumber=" + membersNumber +
//                ", howManyJoin=" + howManyJoin +
//                ", imageGroup=" + imageGroup +
//                '}';
//    }
//}
//}