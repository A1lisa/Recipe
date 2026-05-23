package com.example.recipe.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recipe.App;
import com.example.recipe.R;

public class ViewRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView viewName, viewCategory, viewTimeDifficulty, viewIngredients, viewInstructions;
    private Button editButton, deleteButton, backButton;
    private String recipeId;
    private String[] recipeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        viewName = findViewById(R.id.viewName);
        viewCategory = findViewById(R.id.viewCategory);
        viewTimeDifficulty = findViewById(R.id.viewTimeDifficulty);
        viewIngredients = findViewById(R.id.viewIngredients);
        viewInstructions = findViewById(R.id.viewInstructions);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        backButton = findViewById(R.id.backButton);

        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        recipeId = getIntent().getStringExtra("recipe_id");
        if (recipeId != null) {
            loadRecipe();
        }
    }

    private void loadRecipe() {
        try {
            long id = Long.parseLong(recipeId);
            recipeData = App.getApp().getDBRecipes().find(id);

            viewName.setText(recipeData[1]);
            viewCategory.setText("Категория: " + recipeData[2]);
            viewTimeDifficulty.setText("Время: " + recipeData[5] + " мин | Сложность: " + recipeData[6]);
            viewIngredients.setText(recipeData[3]);
            viewInstructions.setText(recipeData[4]);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка загрузки рецепта", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            finish();
        } else if (v.getId() == R.id.editButton) {
            Intent intent = new Intent(this, AddRecipeActivity.class);
            intent.putExtra("edit_mode", true);
            intent.putExtra("recipe_id", recipeId);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.deleteButton) {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Удаление рецепта")
                    .setMessage("Вы уверены, что хотите удалить рецепт \"" + viewName.getText() + "\"?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        try {
                            long id = Long.parseLong(recipeId);
                            App.getApp().getDBRecipes().delete(id);
                            Toast.makeText(this, R.string.msg_del_success, Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(this, "Ошибка удаления", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Нет", null)
                    .show();
        }
    }
}