package com.example.recipe.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.recipe.App;
import com.example.recipe.model.DBRecipe;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private DBRecipe dbRecipes;
    private ListView mListView;
    private Button add;
    private Button search;
    private ArrayList<String> listRecipes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.addButton);
        search = findViewById(R.id.searchButton);
        add.setOnClickListener(this);
        search.setOnClickListener(this);

        dbRecipes = App.getApp().getDBRecipes();
        mListView = findViewById(R.id.list);

        loadRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes();
    }

    private void loadRecipes() {
        listRecipes = dbRecipes.selectAll();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listRecipes);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addButton) {
            Intent intent = new Intent(this, AddRecipeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.searchButton) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
    }
}