//package com.example.recipe.model;
//
//public class Recipe {
//    private int id;
//    private String name;
//    private String category;
//    private String ingredients;
//    private String instructions;
//    private int cookingTime;
//    private String difficulty;
//
//    public Recipe(String name, String category, String ingredients,
//                  String instructions, int cookingTime, String difficulty) {
//        this(0, name, category, ingredients, instructions, cookingTime, difficulty);
//    }
//
//    public Recipe(int id, String name, String category, String ingredients,
//                  String instructions, int cookingTime, String difficulty) {
//        this.id = id;
//        this.name = name;
//        this.category = category;
//        this.ingredients = ingredients;
//        this.instructions = instructions;
//        this.cookingTime = cookingTime;
//        this.difficulty = difficulty;
//    }
//
//    public int getId() { return id; }
//    public void setId(int id) { this.id = id; }
//    public String getName() { return name; }
//    public String getCategory() { return category; }
//    public String getIngredients() { return ingredients; }
//    public String getInstructions() { return instructions; }
//    public int getCookingTime() { return cookingTime; }
//    public String getDifficulty() { return difficulty; }
//
//    public void setName(String name) { this.name = name; }
//    public void setCategory(String category) { this.category = category; }
//    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
//    public void setInstructions(String instructions) { this.instructions = instructions; }
//    public void setCookingTime(int cookingTime) { this.cookingTime = cookingTime; }
//    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
//
//    public String getFullInfo() {
//        return "РЕЦЕПТ: " + name + "\n" +
//                "Категория: " + category + "\n" +
//                "Время приготовления: " + cookingTime + " минут\n" +
//                "Сложность: " + difficulty + "\n\n" +
//                "ИНГРЕДИЕНТЫ:\n" + ingredients + "\n\n" +
//                "ПРИГОТОВЛЕНИЕ:\n" + instructions;
//    }
//}
package com.example.recipe.model;

public class Recipe {
    private int id;
    private String name;
    private String category;
    private String ingredients;
    private String instructions;
    private int cookingTime;
    private String difficulty;

    public Recipe(String name, String category, String ingredients,
                  String instructions, int cookingTime, String difficulty) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cookingTime = cookingTime;
        this.difficulty = difficulty;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
    public int getCookingTime() { return cookingTime; }
    public String getDifficulty() { return difficulty; }

    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public void setCookingTime(int cookingTime) { this.cookingTime = cookingTime; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}