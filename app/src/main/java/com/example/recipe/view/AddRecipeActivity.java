package com.example.recipe.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recipe.App;
import com.example.recipe.R;
import java.util.ArrayList;
import java.util.Arrays;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button saveButton, exitButton;
    private EditText editName, editIngredients, editInstructions, editTime, editURL;
    private Spinner spinnerCategory, spinnerDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        editName = findViewById(R.id.editName);
        editIngredients = findViewById(R.id.editIngredients);
        editInstructions = findViewById(R.id.editInstructions);
        editTime = findViewById(R.id.editTime);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        editURL = findViewById(R.id.editURL);
        saveButton = findViewById(R.id.saveButton);
        exitButton = findViewById(R.id.exitButton);

        saveButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        ArrayList<String> categories = App.getApp().getDBRecipes().getAllCategories();
        if (categories.isEmpty()) {
            categories = new ArrayList<>(Arrays.asList("Завтрак", "Обед", "Ужин", "Десерт", "Салат", "Супы", "Выпечка"));
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        ArrayList<String> difficulties = new ArrayList<>(Arrays.asList("Легко", "Средне", "Сложно"));
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, difficulties);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(difficultyAdapter);

        boolean isEditMode = getIntent().getBooleanExtra("edit_mode", false);
        if (isEditMode) {
            String editId = getIntent().getStringExtra("recipe_id");
            if (editId != null) {
                long id = Long.parseLong(editId);
                String[] data = App.getApp().getDBRecipes().find(id);
                editName.setText(data[1]);
                for (int i = 0; i < spinnerCategory.getCount(); i++) {
                    if (spinnerCategory.getItemAtPosition(i).toString().equals(data[2])) {
                        spinnerCategory.setSelection(i);
                        break;
                    }
                }
                editIngredients.setText(data[3]);
                editInstructions.setText(data[4]);
                editTime.setText(data[5]);
                String diff = data[6];
                editURL.setText(data[7]);
                if (diff != null) {
                    for (int i = 0; i < spinnerDifficulty.getCount(); i++) {
                        if (spinnerDifficulty.getItemAtPosition(i).toString().equals(diff)) {
                            spinnerDifficulty.setSelection(i);
                            break;
                        }
                    }
                }
                saveButton.setText("Сохранить");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            String name = editName.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String ingredients = editIngredients.getText().toString().trim();
            String instructions = editInstructions.getText().toString().trim();
            String timeStr = editTime.getText().toString().trim();
            String difficulty = spinnerDifficulty.getSelectedItem().toString();
            String url = editURL.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, R.string.msg_empty_field, Toast.LENGTH_LONG).show();
                return;
            }

            int time = 30;
            if (!timeStr.isEmpty()) {
                time = Integer.parseInt(timeStr);
            }

            boolean isEditMode = getIntent().getBooleanExtra("edit_mode", false);
            if (isEditMode) {
                String editId = getIntent().getStringExtra("recipe_id");
                if (editId != null) {
                    int id = Integer.parseInt(editId);
                    App.getApp().getDBRecipes().update(id, name, category, ingredients, instructions, time, difficulty, url);
                    Toast.makeText(this, "Рецепт обновлён!", Toast.LENGTH_SHORT).show();
                }
            } else {
                App.getApp().getDBRecipes().insert(name, category, ingredients, instructions, time, difficulty, url);
                Toast.makeText(this, R.string.msg_add_success, Toast.LENGTH_SHORT).show();
            }
            finish();
        }else if (v.getId() == R.id.exitButton) {
            finish();
        }

    }
}