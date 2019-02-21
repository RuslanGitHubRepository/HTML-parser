package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = Storage.getAllStoreIngredient, query = "SELECT st.ingredients FROM Storage st WHERE st.tailsIngredient > 0"),
        @NamedQuery(name = Storage.getAllStorage, query = "SELECT st FROM Storage st JOIN FETCH st.ingredients WHERE st.tailsIngredient > 0"),
        @NamedQuery(name = Storage.getOneUnit, query = "SELECT st FROM Storage st JOIN FETCH st.ingredients WHERE st.ingredients.name = :name")
})
@Table(name = "storage")
public class Storage  implements Serializable {
    public static final String getAllStoreIngredient = "getAllStoreIngredient";
    public static final String getAllStorage = "getAllStorage";
    public static final String getOneUnit = "getOneUnit";
    public static final String updateUnit = "updateUnit";
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    private Ingredient ingredients;
    @Column(name = "tails")
    private Integer tailsIngredient;
    @Column(name = "cost")
    private Double costIngredient;

    public Storage(){}
    public Storage(Integer tailsIngredient, Double costIngredient,Ingredient ingredients){
        this.tailsIngredient = tailsIngredient;
        this.costIngredient = costIngredient;
        this.ingredients = ingredients;
    }

    public Integer getTailsIngredient() {
        return tailsIngredient;
    }

    public void setTailsIngredient(Integer tailsIngredient) {
        this.tailsIngredient = tailsIngredient;
    }

    public Double getCostIngredient() {
        return costIngredient;
    }

    public void setCostIngredient(Double costIngredient) {
        this.costIngredient = costIngredient;
    }

    public Ingredient getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient ingredients) {
        this.ingredients = ingredients;
    }
}
