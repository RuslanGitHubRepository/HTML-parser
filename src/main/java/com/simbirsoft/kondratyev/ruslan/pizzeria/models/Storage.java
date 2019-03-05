package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "storage")
@NoArgsConstructor
public class Storage  implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Ingredient ingredients;
    @Column(name = "tails")
    private Integer tailsIngredient;
    @Column(name = "cost")
    private Double costIngredient;

    public Storage(Integer tailsIngredient, Double costIngredient,Ingredient ingredients){
        this.tailsIngredient = tailsIngredient;
        this.costIngredient = costIngredient;
        this.ingredients = ingredients;
    }
}
