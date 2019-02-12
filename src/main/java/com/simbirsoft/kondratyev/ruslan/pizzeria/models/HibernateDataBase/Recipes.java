package com.simbirsoft.kondratyev.ruslan.pizzeria.models.HibernateDataBase;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = Recipes.getRecipe, query = "SELECT re.recipe FROM Recipes re WHERE re.recipe.id = :serialNumber")
})
public class Recipes {
    public static final String getRecipe = "getRecipe";
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String nameRecipes;
    @OneToOne(fetch = FetchType.EAGER)
    private Recipe recipe;

    public Recipes(){}

    public String getNameRecipes() {
        return nameRecipes;
    }

    public void setNameRecipes(String nameRecipes) {
        this.nameRecipes = nameRecipes;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}

