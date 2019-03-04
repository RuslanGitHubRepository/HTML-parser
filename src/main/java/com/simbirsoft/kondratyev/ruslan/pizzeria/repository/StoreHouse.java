package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;


import com.simbirsoft.kondratyev.ruslan.Application;
import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.StorageRepository;
import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Store;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Storage;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;

@Service(value = "storeServise")
@Transactional
public class StoreHouse  implements Store<Ingredient> {
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private Logger log;

    private final String getQuantity = "StorageRepository.getQuantity ";
    private final String getIngredient = "StorageRepository.getIngredient ";
    private final String getAllIngredients = "StorageRepository.getAllIngredients ";
    private final String commitStore = "StorageRepository.commitStore ";

    public Wrongs getIngredient(final Ingredient type, final Integer quantity) {
        if (quantity == 0) {
            return WRONG_NONE;
        }
        Integer countIngredientDB = getQuantity(type);
        if ((countIngredientDB < quantity) || (countIngredientDB == 0)) {
            return WRONG_INPUT;
        }
        log.info(getIngredient + Calendar.getInstance().toString());
        return WRONG_NONE;
    }

    public Integer getQuantity(final Ingredient type){
            Integer result = 0;

       Optional<Storage> storageOptional = storageRepository.findByIngredients_NameEquals(type.getName());
        if(storageOptional.isPresent()){
            result = storageOptional.get().getTailsIngredient();
        }
        log.info("\n"+getQuantity + Calendar.getInstance().toString());
       return result;
    }

    public Collection<Ingredient> getAllIngredients() {
        Collection<Ingredient> listIndredients = new ArrayList<>();

        List<Storage> storageList = storageRepository.findByTailsIngredientGreaterThan(0);

        for(Storage storage:storageList){
            listIndredients.add(storage.getIngredients());
        }
        log.info(getAllIngredients + Calendar.getInstance().toString());
        return listIndredients;
    }

    public void commitStore(List<Pair<String,Integer>> ingrediens) {
        if(ingrediens.size() == 0){
            return;
        }
        List<Storage> storageList;

        storageList = storageRepository.findByTailsIngredientGreaterThan(0);

        for(Pair<String, Integer> pair : ingrediens) {
            for (Storage storage: storageList) {

                if(storage.getIngredients().getName() != pair.getFirst()){
                    continue;
                }
                storage.setCostIngredient(0.0);
                storage.setTailsIngredient(storage.getTailsIngredient() - pair.getSecond());
                storageRepository.save(storage);
                break;
            }
        }
        log.info(commitStore + Calendar.getInstance().toString());
    }


}