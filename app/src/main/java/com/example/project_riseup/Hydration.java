package com.example.project_riseup;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "hydration")
public class Hydration {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Long userId;

    private double waterAmount;


    private Date drinkDate; // Use Date directly

    // Constructors, Getters, and Setters
    public Hydration() {}

    public Hydration(Long userId, double waterAmount, Date drinkDate) {
        this.userId = userId;
        this.waterAmount = waterAmount;
        this.drinkDate = drinkDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(double waterAmount) {
        this.waterAmount = waterAmount;
    }

    public Date getDrinkDate() {
        return drinkDate;
    }

    public void setDrinkDate(Date drinkDate) {
        this.drinkDate = drinkDate;
    }

    @Override
    public String toString() {
        return "Hydration{" +
                "id=" + id +
                ", userId=" + userId +
                ", waterAmount=" + waterAmount +
                ", drinkDate=" + drinkDate +
                '}';
    }
}


//import androidx.room.Entity;
//
//import com.google.gson.annotations.SerializedName;
//import java.time.LocalDate;
//import java.util.Date;
//
//@Entity(tableName = "hydration")
//public class Hydration {
//
//    @SerializedName("id")
//    private Long id; // Auto-generated
//
//    @SerializedName("userId")
//    private Long userId;
//
//    @SerializedName("waterAmount")
//    private double waterAmount;
//
//    @SerializedName("amountToDrink")
//    private double amountToDrink;
//
//    private Date drinkDate; // Use LocalDate directly
//
//    // Constructors
//    public Hydration() {
//    }
//
//    public Hydration(Long id, Long userId, double waterAmount, double amountToDrink, Date drinkDate) {
//        this.id = id;
//        this.userId = userId;
//        this.waterAmount = waterAmount;
//        this.amountToDrink = amountToDrink;
//        this.drinkDate = drinkDate;
//    }
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public double getWaterAmount() {
//        return waterAmount;
//    }
//
//    public void setWaterAmount(double waterAmount) {
//        this.waterAmount = waterAmount;
//    }
//
//    public double getAmountToDrink() {
//        return amountToDrink;
//    }
//
//    public void setAmountToDrink(double amountToDrink) {
//        this.amountToDrink = amountToDrink;
//    }
//
//    public Date getDate() {
//        return drinkDate;
//    }
//
//    public void setDate(Date date) {
//        this.drinkDate = date;
//    }
//
//    @Override
//    public String toString() {
//        return "Hydration{" +
//                "id=" + id +
//                ", userId=" + userId +
//                ", waterAmount=" + waterAmount +
//                ", amountToDrink=" + amountToDrink +
//                ", date=" + drinkDate +
//                '}';
//    }
//}
