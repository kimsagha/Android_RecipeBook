package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewIngredients extends AppCompatActivity {

    private IngredientViewModel viewModel;

    // view all ingredients in a recyclerview
    // populate it with an adapter, getting data from the viewmodel that queries the db
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ingredients);

        IngredientAdapter ingredientAdapter = new IngredientAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.ingredientsView);
        recyclerView.setAdapter(ingredientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(IngredientViewModel.class);

        viewModel.getAllIngredients().observe(this, ingredients -> { ingredientAdapter.setData(ingredients); });
    }

    public void onClickBack(View v) {
        finish();
    }

}