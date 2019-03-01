package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Taste;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Condition;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.metamodel.StaticMetamodel;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NamedQueries(value = {
        @NamedQuery(name = Ingredient.getIngredient, query = "SELECT ing FROM Ingredient ing  WHERE ing.name = :name"),
})
@StaticMetamodel(value = Ingredient.class)
@Table(name = "ingredients")
public class Ingredient implements Serializable {
    public static final String getIngredient = "getIngredient";
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", length = 30)
    private String name;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "taste", length =30)
    private Taste taste;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "conditions", length =30)
    private Condition condition;

    public Ingredient(){}

    @Override
    public String toString(){
        return name + ": " + taste.toString() + ", " + condition.toString();
    }


}
