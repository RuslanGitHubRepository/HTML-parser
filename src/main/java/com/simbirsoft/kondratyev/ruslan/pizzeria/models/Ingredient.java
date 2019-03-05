package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Taste;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Condition;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.metamodel.StaticMetamodel;
import java.io.Serializable;

@Entity
@Table(name = "ingredients")
@Data
public class Ingredient implements Serializable {
    public static final String getIngredient = "getIngredient";
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 30)
    private String name;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "taste", length =30)
    private Taste taste;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "conditions", length =30)
    private Condition condition;

}
