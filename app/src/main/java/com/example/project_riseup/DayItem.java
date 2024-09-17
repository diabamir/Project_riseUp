package com.example.project_riseup;
public class DayItem {

    private final String day;
    private final String additionalInfo;

    public DayItem(String day, String additionalInfo) {
        this.day = day;
        this.additionalInfo = additionalInfo;
    }

    public String getDay() {
        return day;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
