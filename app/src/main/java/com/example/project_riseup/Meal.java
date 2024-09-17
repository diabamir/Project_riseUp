package com.example.project_riseup;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meals")
public class Meal {

    @PrimaryKey
    private int id;
    private String mealName;
    private String ingredient1;
    private String ingredient2;
    private String ingredient3;
    private String ingredient4;
    private String quantity1;
    private String quantity2;
    private String quantity3;
    private String quantity4;
    private boolean isChecked;

    // Getters and setters for each field

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(String ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public String getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(String ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public String getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(String ingredient3) {
        this.ingredient3 = ingredient3;
    }

    public String getIngredient4() {
        return ingredient4;
    }

    public void setIngredient4(String ingredient4) {
        this.ingredient4 = ingredient4;
    }

    public String getQuantity1() {
        return quantity1;
    }

    public void setQuantity1(String quantity1) {
        this.quantity1 = quantity1;
    }

    public String getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(String quantity2) {
        this.quantity2 = quantity2;
    }

    public String getQuantity3() {
        return quantity3;
    }

    public void setQuantity3(String quantity3) {
        this.quantity3 = quantity3;
    }

    public String getQuantity4() {
        return quantity4;
    }

    public void setQuantity4(String quantity4) {
        this.quantity4 = quantity4;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
