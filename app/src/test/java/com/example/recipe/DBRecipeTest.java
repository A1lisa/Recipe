package com.example.recipe;

import static org.junit.Assert.*;
import com.example.recipe.model.DBRecipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import java.util.ArrayList;
@RunWith(RobolectricTestRunner.class)
public class DBRecipeTest {

    @Test
    public void insert() {
        DBRecipe db = new DBRecipe(RuntimeEnvironment.getApplication());
        long id = db.insert("Борщ", "Супы", "Свёкла, капуста",
                "Варить 2 часа", 120, "Средне", "");
        assertTrue("ID должен быть > 0", id > 0);
    }

    @Test
    public void update() {
        DBRecipe db = new DBRecipe(RuntimeEnvironment.getApplication());
        long id = db.insert("Старое", "Супы", "...", "...", 30, "Легко", "");
        db.update((int) id, "Новое", "Завтрак", "...", "...", 60, "Средне", "");
        String[] recipe = db.find(id);
        assertEquals("Новое", recipe[1]);
        assertEquals("Завтрак", recipe[2]);
    }

    @Test
    public void delete() {
        DBRecipe db = new DBRecipe(RuntimeEnvironment.getApplication());
        long id = db.insert("Тест", "Тест", "...", "...", 30, "Легко", "");
        int deleted = db.delete(id);
        assertEquals("Должна удалиться 1 запись", 1, deleted);
    }

    @Test
    public void find() {
        DBRecipe db = new DBRecipe(RuntimeEnvironment.getApplication());
        long id = db.insert("Борщ", "Супы", "Свёкла", "Варить", 120, "Средне", "");
        String[] recipe = db.find(id);
        assertEquals("Борщ", recipe[1]);
        assertEquals("Супы", recipe[2]);
        assertEquals("Свёкла", recipe[3]);
    }

    @Test
    public void searchByName() {
        DBRecipe db = new DBRecipe(RuntimeEnvironment.getApplication());
        db.insert("Борщ", "Супы", "...", "...", 120, "Средне", "");
        db.insert("Омлет", "Завтрак", "...", "...", 10, "Легко", "");

        ArrayList<String> result = db.searchByName("Борщ");
        assertTrue("Должен найти борщ", result.size() > 0);

        ArrayList<String> result2 = db.searchByName("несуществует");
        assertEquals("Не должен найти", 0, result2.size());
    }

    @Test
    public void searchByCategory() {
        DBRecipe db = new DBRecipe(RuntimeEnvironment.getApplication());
        db.insert("Борщ", "Супы", "...", "...", 120, "Средне", "");
        db.insert("Омлет", "Завтрак", "...", "...", 10, "Легко", "");

        ArrayList<String> result = db.searchByCategory("Супы");
        assertTrue("Должен найти супы", result.size() > 0);
    }

    @Test
    public void searchByNameAndCategory() {
        DBRecipe db = new DBRecipe(RuntimeEnvironment.getApplication());
        db.insert("Борщ", "Супы", "...", "...", 120, "Средне", "");
        db.insert("Омлет", "Завтрак", "...", "...", 10, "Легко", "");

        ArrayList<String> result = db.searchByNameAndCategory("Борщ", "Супы");
        assertTrue("Должен найти борщ в супах", result.size() > 0);
    }
}