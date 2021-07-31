package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

// foreign keys defined to connect the recipe and ingredient tables
@Entity(
        tableName = "recipe_ingredients",
        foreignKeys = {
                @ForeignKey(
                        entity = Recipe.class,
                        parentColumns = "_id",
                        childColumns = "recipe_id"),
                @ForeignKey(
                        entity = Ingredient.class,
                        parentColumns = "_id",
                        childColumns = "ingredient_id")
        },
        primaryKeys = {"recipe_id", "ingredient_id"}
)
public class RecipeIngredient {

    @NonNull
    private int recipe_id;

    @NonNull
    private int ingredient_id;

    public RecipeIngredient(@NonNull int recipe_id, @NonNull int ingredient_id) {
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
    }

    public int getIngredient_id() {
        return this.ingredient_id;
    }

    public int getRecipe_id() {
        return this.recipe_id;
    }

}
