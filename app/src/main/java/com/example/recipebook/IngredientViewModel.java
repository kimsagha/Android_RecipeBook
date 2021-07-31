package com.example.recipebook;

import android.app.Application;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class IngredientViewModel extends AndroidViewModel {

    private MyRepository repository;

    private final LiveData<List<Ingredient>> allIngredients;

    // instantiate this class in main to access repository functions that use dao functions to query db
    public IngredientViewModel(Application application) {
        super(application);
        repository = new MyRepository(application);
        allIngredients = repository.getAllIngredients();
    }

    LiveData<List<Ingredient>> getAllIngredients() {
        return allIngredients;
    }

    public void insert(Ingredient ingredient) {
        repository.insert(ingredient);
    }

    int getIngredientIdByName(String name) throws ExecutionException, InterruptedException {
        return repository.getIngredientIdByName(name);
    }

}
