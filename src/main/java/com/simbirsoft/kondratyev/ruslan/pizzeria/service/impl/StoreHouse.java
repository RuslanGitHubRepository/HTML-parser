package com.simbirsoft.kondratyev.ruslan.pizzeria.service.impl;


import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.IngredientDto;
import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.IngredientMapper;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StorageRepository;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Storage;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Store;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import javax.transaction.Transactional;
import java.util.*;


import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;

@Service(value = "storeServise")
@Transactional
public class StoreHouse  implements Store<Ingredient> {
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private IngredientMapper ingredientMapper;

    public Wrongs getIngredient(final Ingredient type, final Integer quantity) {
        if (quantity == 0) {
            return WRONG_NONE;
        }
        Integer countIngredientDB = getQuantity(type);
        if ((countIngredientDB < quantity) || (countIngredientDB == 0)) {
            return WRONG_INPUT;
        }

        return WRONG_NONE;
    }
    public Integer getQuantity(final Ingredient type){
        Integer result = 0;
        Optional<Storage> storageOptional = storageRepository.findByIngredients_NameEquals(type.getName());
        if(storageOptional.isPresent()){
            result = storageOptional.get().getTailsIngredient();
        }

       return result;
    }

    public List<IngredientDto> getAllIngredients() {
        List<IngredientDto> listIndredients = new ArrayList<>();

        List<Storage> storageList = storageRepository.findByTailsIngredientGreaterThan(0);

        for(Storage storage:storageList){
            //listIndredients.add((Ingredient)Hibernate.unproxy(storage.getIngredients()));
            listIndredients.add(ingredientMapper.IngredientToIngredientDto(storage.getIngredients()));
        }

        return listIndredients;
    }

    public void commitStore(List<Pair<String,Integer>> ingrediens) {
        if(ingrediens.size() == 0){
            return;
        }

        List<Storage> storageList = storageRepository.findByTailsIngredientGreaterThan(0);

        for(Pair<String, Integer> pair : ingrediens) {
            for (Storage storage: storageList) {

                if(ObjectUtils.nullSafeEquals(storage.getIngredients().getName(), pair.getFirst())){
                   continue;
                }
                storage.setCostIngredient(0.0);
                storage.setTailsIngredient(storage.getTailsIngredient() - pair.getSecond());
                storageRepository.save(storage);
                break;
            }
        }
    }


}