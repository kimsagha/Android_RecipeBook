package com.example.recipebook;

import android.app.Application;
import java.util.List;
import java.util.concurrent.ExecutionException;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecipeViewModel extends AndroidViewModel {

    private MyRepository repository;
    private final LiveData<List<Recipe>> allRecipesByTitle;
    private final LiveData<List<Recipe>> allRecipesByRating;

    public RecipeViewModel(Application application) {
        super(application);
        repository = new MyRepository(application);
        allRecipesByTitle = repository.getAllRecipesByTitle();
        allRecipesByRating = repository.getAllRecipesByRating();
    }

    LiveData<List<Recipe>> getAllRecipesByTitle() {
        return allRecipesByTitle;
    }

    LiveData<List<Recipe>> getAllRecipesByRating() {
        return allRecipesByRating;
    }

    public void insert(Recipe recipe) {
        repository.insert(recipe);
    }

    public void updateRating(Recipe recipe, int newRating) {
        recipe.setRating(newRating);
    }

    public int getRecipeIdByName(String name) throws ExecutionException, InterruptedException {
        return repository.getRecipeIdByName(name);
    }

}
