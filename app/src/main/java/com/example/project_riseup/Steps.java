package com.example.project_riseup;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "steps")
public class Steps {

    @PrimaryKey
    public Date date;  // Use date as the primary key to ensure one entry per day
    public int initialStepCount;  // Initial cumulative step count at the start of the day
    public int stepsTaken;        // Steps taken during the day

    public Steps(Date date, int initialStepCount, int stepsTaken) {
        this.date = date;
        this.initialStepCount = initialStepCount;
        this.stepsTaken = stepsTaken;
    }

    public Date getDate() {
        return date;
    }

    public int getInitialStepCount() {
        return initialStepCount;
    }

    public int getStepsTaken() {
        return stepsTaken;
    }
}
