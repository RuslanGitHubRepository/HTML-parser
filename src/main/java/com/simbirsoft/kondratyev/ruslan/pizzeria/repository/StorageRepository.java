package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    List<Storage> findByTailsIngredientGreaterThan(int tailsIngredient);
    Optional<Storage> findByIngredients_NameEquals(String name);
}