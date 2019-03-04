package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipes {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String nameRecipes;

    public Recipes(){}
    public Recipes(String nameRecipes, Long id){
        this.nameRecipes = nameRecipes;
        this.id = id;
    }
}

