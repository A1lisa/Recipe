package com.example.recipe.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.recipe.R;
import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> recipes;

    public RecipeAdapter(Context context, ArrayList<String> recipes) {
        super(context, R.layout.item_recipe, recipes);
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_recipe, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemCategory = convertView.findViewById(R.id.itemCategory);
        TextView itemTime = convertView.findViewById(R.id.itemTime);

        String recipeStr = recipes.get(position);
        String[] parts = recipeStr.split(" \\| ");

        itemName.setText(parts[1]);
        itemCategory.setText(parts[2]);
        itemTime.setText(parts[3]);

        return convertView;
    }
}