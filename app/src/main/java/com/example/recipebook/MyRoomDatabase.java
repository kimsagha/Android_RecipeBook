package com.example.recipebook;

import android.content.Context;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Recipe.class, Ingredient.class, RecipeIngredient.class}, version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract RecipeIngredientDao recipeIngredientDao();

    private static volatile MyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "recipe_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(createCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // clear db on create
    private static RoomDatabase.Callback createCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.d("g53mdp", "dboncreate");
            databaseWriteExecutor.execute(() -> {
                RecipeDao recipeDao = INSTANCE.recipeDao();
                recipeDao.deleteAll();

                IngredientDao ingredientDao = INSTANCE.ingredientDao();
                ingredientDao.deleteAll();

                RecipeIngredientDao recipeIngredientDao = INSTANCE.recipeIngredientDao();
                recipeIngredientDao.deleteAll();
            });
        }
    };

}
