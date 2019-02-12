package com.simbirsoft.kondratyev.ruslan.pizzeria.models.HibernateDataBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = Storage.getAllUnit, query = "SELECT st FROM Storage st WHERE st.tailsIngredient > 0"),
        @NamedQuery(name = Storage.getOneUnit, query = "SELECT st FROM Storage st WHERE st.ingredients.name = :name"),
        @NamedQuery(name = Storage.updateUnit, query = "UPDATE Storage st SET st.tailsIngredient = ?1 WHERE st.ingredients.name = ?2"),
})
public class Storage  implements Serializable {
    public static final String getAllUnit = "getAllUnit";
    public static final String getOneUnit = "getOneUnit";
    public static final String updateUnit = "updateUnit";
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER)
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
