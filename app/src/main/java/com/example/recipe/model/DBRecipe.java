package com.example.recipe.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBRecipe {
    private SQLiteDatabase dbRecipes;
    private static final String TABLE_CATEGORIES = "categories";

    public DBRecipe(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        dbRecipes = mOpenHelper.getWritableDatabase();
    }

    public long insert(String name, String category, String ingredients,
                       String instructions, int cookingTime, String difficulty) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Category", category);
        contentValues.put("Ingredients", ingredients);
        contentValues.put("Instructions", instructions);
        contentValues.put("CookingTime", cookingTime);
        contentValues.put("Difficulty", difficulty);
        long result = dbRecipes.insert("RECIPES", null, contentValues);
        addCategory(category);
        return result;
    }

    public int update(int id, String name, String category, String ingredients,
                      String instructions, int cookingTime, String difficulty) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Category", category);
        contentValues.put("Ingredients", ingredients);
        contentValues.put("Instructions", instructions);
        contentValues.put("CookingTime", cookingTime);
        contentValues.put("Difficulty", difficulty);
        addCategory(category);
        return dbRecipes.update("RECIPES", contentValues, "Number = ?",
                new String[]{String.valueOf(id)});
    }

    public int delete(long number) {
        return dbRecipes.delete("RECIPES", "Number = ?",
                new String[]{String.valueOf(number)});
    }

    public String[] find(long number) {
        Cursor mCursor = dbRecipes.query("RECIPES", null, "Number = ?",
                new String[]{String.valueOf(number)}, null, null, null);
        String[] recipe = new String[7];
        if (mCursor.moveToFirst()) {
            recipe[0] = Integer.toString(mCursor.getInt(0));  // id
            recipe[1] = mCursor.getString(1);                 // name
            recipe[2] = mCursor.getString(2);                 // category
            recipe[3] = mCursor.getString(3);                 // ingredients
            recipe[4] = mCursor.getString(4);                 // instructions
            recipe[5] = Integer.toString(mCursor.getInt(5));  // cookingTime
            recipe[6] = mCursor.getString(6);                 // difficulty
        }
        mCursor.close();
        return recipe;
    }

    public ArrayList<String> searchByName(String searchText) {
        Cursor mCursor = dbRecipes.rawQuery(
                "SELECT * FROM RECIPES WHERE LOWER(Name) LIKE LOWER(?)",
                new String[]{"%" + searchText + "%"});
        ArrayList<String> arr = new ArrayList<>();
        if (mCursor.moveToFirst()) {
            do {
                arr.add(mCursor.getInt(0) + " | " +
                        mCursor.getString(1) + " | " +
                        mCursor.getString(2) + " | " +
                        mCursor.getInt(5) + " мин | " +
                        mCursor.getString(6));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
    }

    public ArrayList<String> searchByCategory(String category) {
        Cursor mCursor = dbRecipes.query("RECIPES", null, "Category = ?",
                new String[]{category}, null, null, null);
        ArrayList<String> arr = new ArrayList<>();
        if (mCursor.moveToFirst()) {
            do {
                arr.add(mCursor.getInt(0) + " | " +
                        mCursor.getString(1) + " | " +
                        mCursor.getString(2) + " | " +
                        mCursor.getInt(5) + " мин | " +
                        mCursor.getString(6));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
    }

    public ArrayList<String> searchByNameAndCategory(String name, String category) {
        ArrayList<String> arr = new ArrayList<>();
        Cursor mCursor;

        if (category.equals("Все") || category.isEmpty()) {
            mCursor = dbRecipes.rawQuery(
                    "SELECT * FROM RECIPES WHERE LOWER(Name) LIKE LOWER(?)",
                    new String[]{"%" + name + "%"});
        } else {
            mCursor = dbRecipes.rawQuery(
                    "SELECT * FROM RECIPES WHERE LOWER(Name) LIKE LOWER(?) AND Category = ?",
                    new String[]{"%" + name + "%", category});
        }

        if (mCursor.moveToFirst()) {
            do {
                arr.add(mCursor.getInt(0) + " | " +
                        mCursor.getString(1) + " | " +
                        mCursor.getString(2) + " | " +
                        mCursor.getInt(5) + " мин | " +
                        mCursor.getString(6));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
    }

    public ArrayList<String> selectAll() {
        Cursor mCursor = dbRecipes.query("RECIPES", null, null, null, null, null, null);
        ArrayList<String> arr = new ArrayList<>();
        if (mCursor.moveToFirst()) {
            do {
                arr.add(mCursor.getInt(0) + " | " +
                        mCursor.getString(1) + " | " +
                        mCursor.getString(2) + " | " +
                        mCursor.getInt(5) + " мин | " +
                        mCursor.getString(6));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
    }

    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = getCategoriesFromTable();
        if (categories.isEmpty()) {
            Cursor mCursor = dbRecipes.rawQuery("SELECT DISTINCT Category FROM RECIPES ORDER BY Category", null);
            if (mCursor.moveToFirst()) {
                do {
                    categories.add(mCursor.getString(0));
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
        return categories;
    }

    public ArrayList<String> getCategoriesFromTable() {
        ArrayList<String> categories = new ArrayList<>();
        Cursor cursor = dbRecipes.query(TABLE_CATEGORIES, null, null, null, null, null, "Name ASC");
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(cursor.getColumnIndexOrThrow("Name")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public void addCategory(String name) {
        ContentValues values = new ContentValues();
        values.put("Name", name);
        dbRecipes.insertWithOnConflict(TABLE_CATEGORIES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, "recipes.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE RECIPES ("
                    + "Number integer primary key autoincrement,"
                    + "Name text,"
                    + "Category text,"
                    + "Ingredients text,"
                    + "Instructions text,"
                    + "CookingTime integer,"
                    + "Difficulty text" + ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS categories ("
                    + "id integer primary key autoincrement,"
                    + "Name text UNIQUE" + ");");

            String[] defaultCategories = {"Завтрак", "Обед", "Ужин", "Десерт", "Салат", "Супы", "Выпечка"};
            for (String cat : defaultCategories) {
                ContentValues values = new ContentValues();
                values.put("Name", cat);
                db.insertWithOnConflict("categories", null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS RECIPES");
            db.execSQL("DROP TABLE IF EXISTS categories");
            onCreate(db);
        }
    }
}