package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Recipe implements Serializable {
    public static final String insertUnit = "insertUnit";
    @Id@GeneratedValue
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Ingredient ingredients;
    private Integer recipeNumber;
    private Integer countIngredient;

    public Recipe(){}

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

    public Integer getRecipeNumber() {
        return recipeNumber;
    }

    public void setRecipeNumber(Integer recipeNumber) {
        this.recipeNumber = recipeNumber;
    }
}
