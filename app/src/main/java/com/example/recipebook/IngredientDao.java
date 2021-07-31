package com.example.recipebook;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface IngredientDao {

    // query db with regards to ingredient objects
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ingredient ingredient);

    @Query("DELETE FROM ingredients")
    void deleteAll();

    @Query("SELECT * FROM ingredients GROUP BY ingredientname")
    LiveData<List<Ingredient>> getIngredients();

    // get the id of an ingredient based on its name (name is unique)
    // use id in main to create recipeIngredient objects
    @Query("SELECT _id from ingredients WHERE ingredientname = :name")
    int getIngredientIdByName(String name);

}
