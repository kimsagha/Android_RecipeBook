package com.example.recipebook;

import android.app.Application;
import android.database.Cursor;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import androidx.lifecycle.LiveData;

public class MyRepository {

    private RecipeDao recipeDao;
    private IngredientDao ingredientDao;
    private RecipeIngredientDao recipeIngredientDao;

    private LiveData<List<Recipe>> allRecipesByTitle;
    private LiveData<List<Recipe>> allRecipesByRating;
    private LiveData<List<Ingredient>> allIngredients;
    private LiveData<List<RecipeIngredient>> allRecipeIngredients;

    MyRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);

        ingredientDao = db.ingredientDao();
        recipeDao = db.recipeDao();
        recipeIngredientDao = db.recipeIngredientDao();

        allIngredients = ingredientDao.getIngredients();
        allRecipesByTitle = recipeDao.getRecipesByTitle();
        allRecipesByRating = recipeDao.getRecipesByRating();
        allRecipeIngredients = recipeIngredientDao.getRecipeIngredients();
    }

    LiveData<List<Recipe>> getAllRecipesByTitle() {
        return allRecipesByTitle;
    }

    LiveData<List<Recipe>> getAllRecipesByRating() {
        return allRecipesByRating;
    }

    LiveData<List<Ingredient>> getAllIngredients() {
        return allIngredients;
    }

    LiveData<List<RecipeIngredient>> getAllRecipeIngredients() {
        return allRecipeIngredients;
    }

    int getRecipeIdByName(String name) throws ExecutionException, InterruptedException {
        Future<Integer> future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Integer>(){
            public Integer call() throws Exception {
                return recipeDao.getRecipeIdByName(name);
            }
        });
        int recipeID = future.get();
        return recipeID;
    }

    int getIngredientIdByName(String name) throws ExecutionException, InterruptedException {
        Future<Integer> future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Integer>(){
            public Integer call() throws Exception {
                return ingredientDao.getIngredientIdByName(name);
            }
        });
        int ingredientID = future.get();
        return ingredientID;
    }

    // get the ingredients of a recipe from the recipeIngredientViewModel by giving it the recipeID
    // result of computation is cursor
    // iterate through cursor column for ingredient names to create a string array to send back to main
    String[] getIngredientsOfRecipe(int recipeID) throws ExecutionException, InterruptedException {
        Future<Cursor> future = MyRoomDatabase.databaseWriteExecutor.submit(() -> {
            return recipeIngredientDao.getIngredientsOfRecipe(recipeID);
        });
        Cursor c = future.get();
        String[] ingredients = new String[c.getCount()];
        int i = 0;
        c.moveToFirst();
        while(!c.isAfterLast()) {
            ingredients[i] = c.getString(3);
            i++;
            c.moveToNext();
        }
        c.close();
        return ingredients;
    }

    void insert(Recipe recipe) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipeDao.insert(recipe);
        });
    }

    void insert(Ingredient ingredient) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            ingredientDao.insert(ingredient);
        });
    }

    void insert(RecipeIngredient recipeIngredient) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipeIngredientDao.insert(recipeIngredient);
        });
    }

}
