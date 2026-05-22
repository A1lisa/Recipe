package com.example.recipe.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recipe.App;
import com.example.recipe.R;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button saveButton;
    private Button exitButton;
    private EditText editName, editCategory, editIngredients, editInstructions, editTime, editDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        editName = findViewById(R.id.editName);
        editCategory = findViewById(R.id.editCategory);
        editIngredients = findViewById(R.id.editIngredients);
        editInstructions = findViewById(R.id.editInstructions);
        editTime = findViewById(R.id.editTime);
        editDifficulty = findViewById(R.id.editDifficulty);
        saveButton = findViewById(R.id.saveButton);
        exitButton = findViewById(R.id.exitButton);

        saveButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            String name = editName.getText().toString().trim();
            String category = editCategory.getText().toString().trim();
            String ingredients = editIngredients.getText().toString().trim();
            String instructions = editInstructions.getText().toString().trim();
            String timeStr = editTime.getText().toString().trim();
            String difficulty = editDifficulty.getText().toString().trim();

            if (name.isEmpty() || category.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                Toast.makeText(this, R.string.msg_empty_field, Toast.LENGTH_LONG).show();
                return;
            }

            int time = 30;
            if (!timeStr.isEmpty()) {
                time = Integer.parseInt(timeStr);
            }

            App.getApp().getDBRecipes().insert(name, category, ingredients, instructions, time, difficulty);
            Toast.makeText(this, R.string.msg_add_success, Toast.LENGTH_LONG).show();
            finish();
        } else if (v.getId() == R.id.exitButton) {
            finish();
        }
    }
}