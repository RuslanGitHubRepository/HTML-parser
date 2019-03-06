package com.simbirsoft.kondratyev.ruslan.pizzeria.dto;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Condition;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Taste;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientDto {
    private Long id;
    private String name;
    private Taste taste;
    private Condition condition;
}
