package com.simbirsoft.kondratyev.ruslan.pizzeria.dto;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {
    //IngredientMapper INSTANCE = Mappers.getMapper( IngredientMapper.class );
    IngredientDto IngredientToIngredientDto(Ingredient ingredient);
}
