package com.example.recipe;

import android.app.Application;
import com.example.recipe.model.DBRecipe;

public class App extends Application {
    private static App app;
    private DBRecipe dbRecipe;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static synchronized App getApp() {
        return app;
    }

    public synchronized DBRecipe getDBRecipes() {
        if (dbRecipe == null) {
            dbRecipe = new DBRecipe(this.getApplicationContext());
        }
        return dbRecipe;
    }
}