package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredients")
public class Ingredient {

    // primary key is id
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int _id;

    @NonNull
    private String ingredientname;

    // constructor for nonNull primary key
    public Ingredient(@NonNull String ingredientname, @NonNull int _id) {
        this.ingredientname = ingredientname;
        this._id = _id;
    }

    public String getIngredientname() {
        return ingredientname;
    }

    public int get_id() {
        return _id;
    }

}