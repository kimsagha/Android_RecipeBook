package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddRecipe extends AppCompatActivity {

    private EditText titleInput;
    private EditText ingredientsInput;
    private EditText instructionsInput;
    private EditText ratingInput;

    private String recipeTitle;
    private String recipeIngredients;
    private String recipeInstructions;
    private int recipeRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // get the editText views where the user inputs new data
        titleInput = findViewById((R.id.textTitle));
        ingredientsInput = findViewById((R.id.textIngredients));
        instructionsInput = findViewById(R.id.textInstructions);
        ratingInput = findViewById(R.id.textRating);
    }

    public void onClickBack(View v) {
        // read new data into strings and put them into a new recipe object to send back to main
        recipeTitle = String.valueOf(titleInput.getText());
        recipeIngredients = String.valueOf(ingredientsInput.getText());
        recipeInstructions = String.valueOf(instructionsInput.getText());
        recipeRating = Integer.valueOf(String.valueOf(ratingInput.getText())).intValue();

        // if rating is not between 1 and 5, alert the user and clear the input so they can input new data
        if(recipeRating < 1 || recipeRating > 5) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Rating must be a number between 1 and 5.");
            alertDialogBuilder.setTitle("Rating Input Wrong");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            ratingInput.setText("");
        } else {
            Intent intent = new Intent(AddRecipe.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ingredients", recipeIngredients);
            intent.putExtras(bundle);
            Recipe recipe = new Recipe(recipeTitle, recipeInstructions, recipeRating, 0);
            intent.putExtra("recipe", recipe);
            setResult(1, intent);
            finish();
        }
    }
}