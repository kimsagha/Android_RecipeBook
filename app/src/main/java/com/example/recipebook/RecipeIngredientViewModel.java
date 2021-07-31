package com.example.recipebook;

import android.app.Application;
import android.database.Cursor;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecipeIngredientViewModel extends AndroidViewModel {

    private MyRepository repository;
    private final LiveData<List<RecipeIngredient>> allRecipeIngredients;

    public RecipeIngredientViewModel(Application application) {
        super(application);
        repository = new MyRepository(application);
        allRecipeIngredients = repository.getAllRecipeIngredients();
    }

    LiveData<List<RecipeIngredient>> getAllRecipeIngredients() {
        return allRecipeIngredients;
    }

    public void insert(RecipeIngredient recipeIngredient) {
        repository.insert(recipeIngredient);
    }

    // get a string array of all the ingredients of a recipe based on its id
    String[] getIngredientsOfRecipe(int recipeID) throws ExecutionException, InterruptedException {
        String[] recipeIngredients = repository.getIngredientsOfRecipe(recipeID);
        return recipeIngredients;
    }

}
