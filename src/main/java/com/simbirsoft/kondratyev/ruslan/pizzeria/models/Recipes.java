package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameRecipes;

}

