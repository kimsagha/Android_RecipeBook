package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ViewRecipe extends AppCompatActivity {

    private Recipe recipe;
    private TextView recipeRating;

    // view details of a recipe, enable user to modify rating
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        String title = recipe.getName();
        TextView recipeTitle = findViewById(R.id.recipeTitle);
        recipeTitle.setText(title);

        Bundle bundle = getIntent().getExtras();
        String ingredients = bundle.getString("ingredientText");
        TextView recipeIngredients = findViewById(R.id.recipeIngredients);
        recipeIngredients.setText(ingredients);

        String instructions = recipe.getInstructions();
        TextView recipeInstructions = findViewById(R.id.recipeInstructions);
        recipeInstructions.setText(instructions);

        int rating = recipe.getRating();
        recipeRating = findViewById(R.id.recipeRating);
        recipeRating.setText(String.valueOf(rating));
    }

    // send rating back if it's between 1 and 5
    // if not, ask user to give new rating
    public void onClickBack(View v) {
        recipeRating = findViewById(R.id.recipeRating);
        int updatedRating = Integer.valueOf(String.valueOf(recipeRating.getText()));

        if(updatedRating < 1 || updatedRating > 5) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Rating must be a number between 1 and 5.");
            alertDialogBuilder.setTitle("Rating Input Wrong");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            recipeRating.setText("");
        } else {
            Intent intent = new Intent(ViewRecipe.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("recipeRating", updatedRating);
            intent.putExtras(bundle);
            setResult(2, intent);
            finish();
        }
    }
}