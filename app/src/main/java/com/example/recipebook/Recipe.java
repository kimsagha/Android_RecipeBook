package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

// implement Recipe entity class as serializable to be able to send new Recipe objects back to main with intents
@Entity(tableName = "recipes")
public class Recipe implements Serializable {

    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int _id;

    @NonNull
    private String name;

    @NonNull
    private String instructions;

    private int rating;

    // constructor, only rating can be null, can be set later when viewing an existing recipe
    public Recipe(@NonNull String name, @NonNull String instructions, int rating, @NonNull int _id) {
        this.name = name;
        this.instructions = instructions;
        this.rating = rating;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int newRating) {
        this.rating = newRating;
    }

    public int get_id() {
        return _id;
    }

}
