package com.example.recipe.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.recipe.App;
import com.example.recipe.R;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity implements View.OnClickListener {
    private ListView mListView;
    private Button addButton, searchBtn, showAllBtn;
    private EditText searchEdit;
    private Spinner spinnerCategory;
    private ArrayList<String> listRecipes;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list);
        addButton = findViewById(R.id.addButton);
        searchBtn = findViewById(R.id.searchBtn);
        showAllBtn = findViewById(R.id.showAllBtn);
        searchEdit = findViewById(R.id.searchEdit);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        addButton.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        showAllBtn.setOnClickListener(this);

        searchEdit.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                String category = spinnerCategory.getSelectedItem().toString();

                if (query.isEmpty()) {
                    if (!category.equals("Все")) {
                        listRecipes = App.getApp().getDBRecipes().searchByCategory(category);
                    } else {
                        loadAllRecipes();
                    }
                } else {
                    listRecipes = App.getApp().getDBRecipes().searchByNameAndCategory(query, category);
                }

                adapter = new RecipeAdapter(MainActivity.this, listRecipes);
                mListView.setAdapter(adapter);
            }
            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        loadCategories();

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                if (category.equals("Все")) {
                    loadAllRecipes();
                } else {
                    listRecipes = App.getApp().getDBRecipes().searchByCategory(category);
                    adapter = new RecipeAdapter(MainActivity.this, listRecipes);
                    mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadAllRecipes();
            }
        });

        loadAllRecipes();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = listRecipes.get(position);
                String recipeId = selected.split(" \\| ")[0];
                Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
                intent.putExtra("recipe_id", recipeId);
                startActivity(intent);
            }
        });
    }

    private void loadCategories() {
        ArrayList<String> categories = App.getApp().getDBRecipes().getAllCategories();
        if (categories.isEmpty()) {
            categories = new ArrayList<>(Arrays.asList("Завтрак", "Обед", "Ужин", "Десерт", "Салат", "Супы", "Выпечка"));
        }
        categories.add(0, "Все");
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(catAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllRecipes();
        loadCategories();
    }

    private void loadAllRecipes() {
        listRecipes = App.getApp().getDBRecipes().selectAll();
        adapter = new RecipeAdapter(this, listRecipes);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addButton) {
            Intent intent = new Intent(this, AddRecipeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.searchBtn) {
            String query = searchEdit.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();

            if (query.isEmpty()) {
                if (!category.equals("Все")) {
                    listRecipes = App.getApp().getDBRecipes().searchByCategory(category);
                } else {
                    loadAllRecipes();
                }
            } else {
                listRecipes = App.getApp().getDBRecipes().searchByNameAndCategory(query, category);
            }

            adapter = new RecipeAdapter(this, listRecipes);
            mListView.setAdapter(adapter);
        } else if (v.getId() == R.id.showAllBtn) {
            searchEdit.setText("");
            spinnerCategory.setSelection(0);
            loadAllRecipes();
        }
    }
}