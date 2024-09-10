package com.example.project_riseup;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Nutrition")
public class Nutrition {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private byte[] image; // Use byte[] instead of Bitmap for Room compatibility

    // Public constructor that matches field names and types
    public Nutrition(long id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    // Room needs an empty constructor too
    @Ignore
    public Nutrition() {}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
