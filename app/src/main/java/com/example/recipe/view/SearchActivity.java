package com.example.recipe.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recipe.App;
import com.example.recipe.R;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private Button searchByName, searchByCategory, deleteButton, okButton, exitButton;
    private EditText editInput;
    private ListView searchList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> results;
    private int currentAction = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchByName = findViewById(R.id.searchByNameButton);
        searchByCategory = findViewById(R.id.searchByCategoryButton);
        deleteButton = findViewById(R.id.deleteButton);
        okButton = findViewById(R.id.okButton);
        exitButton = findViewById(R.id.exitButton);
        editInput = findViewById(R.id.editInput);
        searchList = findViewById(R.id.searchList);

        searchByName.setOnClickListener(this);
        searchByCategory.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.searchByNameButton) {
            currentAction = 1;
            editInput.setHint("Введите название рецепта");
        } else if (id == R.id.searchByCategoryButton) {
            currentAction = 2;
            editInput.setHint("Введите категорию");
        } else if (id == R.id.deleteButton) {
            currentAction = 3;
            editInput.setHint("Введите номер рецепта для удаления");
        } else if (id == R.id.okButton) {
            performAction();
        } else if (id == R.id.exitButton) {
            finish();
        }
    }

    private void performAction() {
        String input = editInput.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Введите данные!", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (currentAction) {
            case 1:
                results = App.getApp().getDBRecipes().searchByName(input);
                break;
            case 2:
                results = App.getApp().getDBRecipes().searchByCategory(input);
                break;
            case 3:
                try {
                    long number = Long.parseLong(input);
                    int deleted = App.getApp().getDBRecipes().delete(number);
                    if (deleted > 0) {
                        Toast.makeText(this, R.string.msg_del_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.msg_error, Toast.LENGTH_SHORT).show();
                    }
                    results = App.getApp().getDBRecipes().selectAll();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Введите корректный номер!", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }

        if (results != null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
            searchList.setAdapter(adapter);
        }
    }
}