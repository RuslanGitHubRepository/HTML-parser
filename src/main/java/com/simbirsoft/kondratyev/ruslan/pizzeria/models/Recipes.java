package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipes {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String nameRecipes;

    public Recipes(){}
    public Recipes(String nameRecipes, Integer id){
        this.nameRecipes = nameRecipes;
        this.id = id;
    }

    public String getNameRecipes() {
        return nameRecipes;
    }

    public void setNameRecipes(String nameRecipes) {
        this.nameRecipes = nameRecipes;
    }
}

