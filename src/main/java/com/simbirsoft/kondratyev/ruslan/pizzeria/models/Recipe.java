package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
/*@NamedQueries(value = {
        @NamedQuery(name = Recipe.getRecipe, query = "SELECT rc FROM Recipe rc JOIN FETCH rc.ingredients JOIN FETCH rc.recipeNumber WHERE rc.recipeNumber.id = :serialNumber"),
        @NamedQuery(name = Recipe.deleteRecipes, query = "DELETE FROM Recipe re WHERE re.recipeNumber.id = :idNumber")
})*/
@NoArgsConstructor
@Table(name = "recipe")
public class Recipe implements Serializable {
    public static final String getRecipe = "getRecipe";
    public static final String deleteRecipes = "deleteRecipes";
    /*public static final String insertUnit = "insertUnit";*/
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Ingredient ingredients;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipes recipeNumber;

    private Integer countIngredient;

    public Recipe(Recipes recipeNumber){
        this.recipeNumber = recipeNumber;
    }

}
