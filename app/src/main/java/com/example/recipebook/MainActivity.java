package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener {

    private RecipeViewModel recipeViewModel;
    private IngredientViewModel ingredientViewModel;
    private RecipeIngredientViewModel recipeIngredientViewModel;

    private RecipeAdapter recipeAdapter;
    private Button sortButton;
    private boolean sortTitle = true;
    private Recipe clickedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create recipe adapter with data from recipe view model to show in main activity recyclerview
        recipeAdapter = new RecipeAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.view);
        recipeAdapter.setClickListener(this);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RecipeViewModel.class);

        recipeViewModel.getAllRecipesByTitle().observe(this, recipes -> { recipeAdapter.setData(recipes); });

        ingredientViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(IngredientViewModel.class);

        recipeIngredientViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RecipeIngredientViewModel.class);
    }

    public void onClickSort(View v) {
        // sort recipes of main activity by title or rating
        sortButton = findViewById(R.id.button3);
        if (sortTitle == true) {
            sortTitle = false;
            sortButton.setText("SORT BY TITLE");
            recipeViewModel.getAllRecipesByRating().observe(this, recipes -> { recipeAdapter.setData(recipes); });
        } else {
            sortTitle = true;
            sortButton.setText("SORT BY RATING");
            recipeViewModel.getAllRecipesByTitle().observe(this, recipes -> { recipeAdapter.setData(recipes); });
        }
    }

    // go to activity that adds a new recipe, request code 1 to get the new recipe back
    public void onClickAdd(View v) {
        Intent intent = new Intent(MainActivity.this, AddRecipe.class);
        startActivityForResult(intent, 1);
    }

    // view ingredients in a new activity
    public void onClickViewIngredients(View v) {
        Intent intent = new Intent(MainActivity.this, ViewIngredients.class);
        startActivity(intent);
    }

    // results from activities started from main
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // get new recipe back and insert into database
        // sleep before attempting to access the id
        if(requestCode == 1) {
            Recipe recipe = (Recipe) data.getSerializableExtra("recipe");
            recipeViewModel.insert(recipe);

            int recipeID = 0;
            try {
                Thread.sleep(500);
                recipeID = recipeViewModel.getRecipeIdByName(recipe.getName());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // get the ingredients from the new recipe and insert them into the db as new ingredient objects
            // sleep before attempting to access the id
            String ingredients = data.getStringExtra("ingredients");
            String ingredientsArray[] = ingredients.split("\\r?\\n");
            for(int i = 0; i < ingredientsArray.length; i++) {
                Ingredient ingredient = new Ingredient(ingredientsArray[i], 0);
                ingredientViewModel.insert(ingredient); // only insert if it doesn't already exist
                int ingredientID = 0;
                try {
                    Thread.sleep(500);
                    ingredientID = ingredientViewModel.getIngredientIdByName(ingredient.getIngredientname());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // create recipeIngredient objects to connect the new recipe to its ingredients
                RecipeIngredient recipeIngredient = new RecipeIngredient(recipeID, ingredientID);
                recipeIngredientViewModel.insert(recipeIngredient);
            }
        }

        // get new rating and update db
        if(requestCode == 2) {
            int rating = data.getIntExtra("recipeRating", 1);
            recipeViewModel.updateRating(clickedRecipe, rating);
        }
    }

    // click on a recipe to see its ingredients, getting them from the recipeIngredientViewModel
    // start the activity for result with request code 2 in case rating has been modified
    @Override
    public void onItemClick(View view, int position) throws ExecutionException, InterruptedException {
        clickedRecipe = recipeAdapter.getItem(position);
        String[] ingredients = recipeIngredientViewModel.getIngredientsOfRecipe(clickedRecipe.get_id());
        String ingredientText = "";
        for(int i = 0; i < ingredients.length; i++) {
            ingredientText += ingredients[i] + ", ";
        }
        ingredientText = ingredientText.substring(0, ingredientText.length() - 2);
        Intent intent = new Intent(MainActivity.this, ViewRecipe.class);
        Bundle bundle = new Bundle();
        bundle.putString("ingredientText", ingredientText);
        intent.putExtras(bundle);
        intent.putExtra("recipe", clickedRecipe);
        startActivityForResult(intent, 2);
    }

}