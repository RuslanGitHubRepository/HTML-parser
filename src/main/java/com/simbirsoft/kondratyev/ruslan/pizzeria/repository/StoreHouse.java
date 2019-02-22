package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;

import com.simbirsoft.kondratyev.ruslan.pizzeria.HibernateUtil;
import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Store;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Storage;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;

@Service(value = "storeServise")
@Transactional
public class StoreHouse  implements Store<Ingredient> {

    public Wrongs getIngredient(final Ingredient type, final Integer quantity) {
        if (quantity == 0) {
            return WRONG_NONE;
        }
        if (quantity == Dialog.ABORT) {
            return WRONG_WASHOUT;
        }
        Integer countIngredientDB = getQuantity(type);
        if ((countIngredientDB < quantity) || (countIngredientDB == 0)) {
            return WRONG_INPUT;
        }
        return WRONG_NONE;
    }

    public Integer getQuantity(final Ingredient type){
        HibernateUtil.openSession();

        TypedQuery<Storage> query = HibernateUtil.getSession().createNamedQuery(Storage.getOneUnit,Storage.class);
        query.setParameter("name",type.getName());
        Storage storage = query.getSingleResult();

        HibernateUtil.commitSession();
       return storage.getTailsIngredient();
    }

    public Collection<Ingredient> getAllIngredients() {
        Collection<Ingredient> listIndredients;
        HibernateUtil.openSession();
        TypedQuery<Ingredient> query = HibernateUtil.getSession().createNamedQuery(Storage.getAllStoreIngredient, Ingredient.class);
        listIndredients = query.getResultList();

        HibernateUtil.commitSession();

        return listIndredients;
    }

    public void commitStore(List<Pair<String,Integer>> ingrediens) {
        if(ingrediens.size() == 0){
            return;
        }
        List<Storage> listStorage = new ArrayList<>();

        HibernateUtil.openSession();

        TypedQuery<Storage> query = HibernateUtil.getSession().createNamedQuery(Storage.getAllStorage, Storage.class);
        listStorage = query.getResultList();
        for(Pair<String, Integer> pair : ingrediens) {
            for (Storage storage: listStorage) {

                if(storage.getIngredients().getName() != pair.getFirst()){
                    continue;
                }
                storage.setCostIngredient(0.0);
                storage.setTailsIngredient(storage.getTailsIngredient() - pair.getSecond());
                HibernateUtil.getSession().saveOrUpdate(storage);
                break;
            }
        }
        HibernateUtil.commitSession();
    }
}