package com.example.recipebook;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface RecipeIngredientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RecipeIngredient recipeIngredient);

    @Query("DELETE FROM recipe_ingredients")
    void deleteAll();

    @Query("SELECT * FROM recipe_ingredients ORDER BY recipe_id ASC")
    LiveData<List<RecipeIngredient>> getRecipeIngredients();

    // get a cursor pointing to the ingredients of a recipe based on its id
    @Query("SELECT r._id AS recipe_id, r.name, ri.ingredient_id, i.ingredientname  FROM recipes r JOIN recipe_ingredients ri ON (r._id = ri.recipe_id) JOIN ingredients i ON (ri.ingredient_id = i._id) WHERE r._id = :recipeID")
    Cursor getIngredientsOfRecipe(int recipeID);

}
