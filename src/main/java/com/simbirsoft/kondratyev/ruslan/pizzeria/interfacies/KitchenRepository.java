package com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KitchenRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByRecipeNumber_IdEquals(Integer id);
    void deleteRecipeByRecipeNumber_IdEquals(Integer id);
}
