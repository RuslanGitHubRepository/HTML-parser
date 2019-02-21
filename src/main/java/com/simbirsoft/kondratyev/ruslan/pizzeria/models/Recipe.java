package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = Recipe.getRecipe, query = "SELECT re FROM Recipe re JOIN FETCH re.ingredients JOIN FETCH re.recipeNumber WHERE re.recipeNumber.id = :serialNumber"),
        @NamedQuery(name = Recipe.deleteRecipes, query = "DELETE FROM Recipe re WHERE re.recipeNumber.id = :idNumber")
})
@Table(name = "recipe")
public class Recipe implements Serializable {
    public static final String getRecipe = "getRecipe";
    public static final String deleteRecipes = "deleteRecipes";
    /*public static final String insertUnit = "insertUnit";*/
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private Ingredient ingredients;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipes recipeNumber;

    private Integer countIngredient;

    public Recipe(){}
    public Recipe(Recipes recipeNumber){
        this.recipeNumber = recipeNumber;
    }

    public Integer setCountIngredient() {
        return countIngredient;
    }

    public void setCountIngredient(Integer countIngredient) {
        this.countIngredient = countIngredient;
    }

    public Ingredient getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient ingredients) {
        this.ingredients = ingredients;
    }

    public Recipes getRecipeNumber() {
        return recipeNumber;
    }

    public void setRecipeNumber(Recipes recipeNumber) {
        this.recipeNumber = recipeNumber;
    }
}
