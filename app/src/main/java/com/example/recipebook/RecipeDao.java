package com.example.recipebook;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Recipe recipe);

    @Query("DELETE FROM recipes")
    void deleteAll();

    // query db for all recipes and order them by name in ascending order
    @Query("SELECT * from recipes ORDER BY name ASC")
    LiveData<List<Recipe>> getRecipesByTitle();

    // query db for all recipes and order them by rating in descending order
    @Query("SELECT * from recipes ORDER BY rating DESC")
    LiveData<List<Recipe>> getRecipesByRating();

    // get id of recipe based on name
    @Query("SELECT _id from recipes WHERE name = :name")
    int getRecipeIdByName(String name);

}
