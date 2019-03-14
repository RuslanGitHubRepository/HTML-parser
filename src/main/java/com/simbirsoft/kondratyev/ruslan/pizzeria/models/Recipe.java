package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "recipe")
public class Recipe implements Serializable {
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
