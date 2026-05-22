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
import android.widget.Toast;
import com.example.recipe.App;
import com.example.recipe.R;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private ListView mListView;
    private Button addButton, searchBtn, showAllBtn;
    private EditText searchEdit;
    private ArrayList<String> listRecipes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list);
        addButton = findViewById(R.id.addButton);
        searchBtn = findViewById(R.id.searchBtn);
        showAllBtn = findViewById(R.id.showAllBtn);
        searchEdit = findViewById(R.id.searchEdit);

        addButton.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        showAllBtn.setOnClickListener(this);

        loadAllRecipes();

        // При клике на элемент списка — открываем просмотр
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = listRecipes.get(position);
                // Извлекаем ID рецепта (первое число до " | ")
                String recipeId = selected.split(" \\| ")[0];
                Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
                intent.putExtra("recipe_id", recipeId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllRecipes();
    }

    private void loadAllRecipes() {
        listRecipes = App.getApp().getDBRecipes().selectAll();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listRecipes);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addButton) {
            Intent intent = new Intent(this, AddRecipeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.searchBtn) {
            String query = searchEdit.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(this, "Введите название для поиска", Toast.LENGTH_SHORT).show();
                return;
            }
            listRecipes = App.getApp().getDBRecipes().searchByName(query);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listRecipes);
            mListView.setAdapter(adapter);
        } else if (v.getId() == R.id.showAllBtn) {
            searchEdit.setText("");
            loadAllRecipes();
        }
    }
}