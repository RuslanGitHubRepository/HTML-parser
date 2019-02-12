package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.HibernateDataBase.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.*;

import java.util.Iterator;
import java.util.List;


public class DataExchange {
    private static SessionFactory sessionFactory;
    private static Session session;
    private DataExchange(){}

    private static void openSession(){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }
    private static void closeSession(){
       session.close();
       sessionFactory.close();
    }


    public static Storage getRequestStorage(Ingredient ingredient){
        openSession();

        session.beginTransaction();
        TypedQuery<Storage> query = session.createNamedQuery(Storage.getOneUnit,Storage.class);
        Storage storage = query.getSingleResult();

        closeSession();
        return storage;
    }
    public static List<Storage> getRequestStorageAll(){
        openSession();

        session.beginTransaction();
        TypedQuery<Storage> query = session.createNamedQuery(Storage.getAllUnit,Storage.class);
        List<Storage> list = query.getResultList();

        closeSession();
        return list;

    }
    public static void updateStorage(List<Storage> listStorage){
        openSession();

        session.beginTransaction();
        Query query = session.createNamedQuery(Storage.updateUnit);
        List<Storage> storages = getRequestStorageAll();
        Iterator<Storage> itStorage = storages.iterator();
        Integer ingr = null;
        for(Storage storage:listStorage) {
            ingr = itStorage.next().getTailsIngredient();
            query.setParameter(1, (ingr - storage.getTailsIngredient()));
            query.setParameter(2, storage.getIngredients().getName());
            query.executeUpdate();
        }

        closeSession();
    }

    public static void updateRecipe(Integer numberRecipe, Integer countIngredient, Ingredient ingredient){
        openSession();
        session.beginTransaction();

        TypedQuery<Ingredient> queryType = session.createNamedQuery(Ingredient.getIngredient,Ingredient.class);
        queryType.setParameter("name",ingredient.getName());
        Ingredient ingredientTemporary = queryType.getSingleResult();

        Query query = session.createNamedQuery(Recipe.insertUnit);
        query.setParameter(1, countIngredient);
        query.setParameter(2, numberRecipe);
        query.setParameter(3, ingredientTemporary.getId());

        closeSession();

    }
    public static List<Recipe> getRecipe(Integer numberRecipe){
        openSession();
        session.beginTransaction();

        TypedQuery<Recipe> queryType = session.createNamedQuery(Recipes.getRecipe,Recipe.class);
        queryType.setParameter("serialNumber",numberRecipe);
        List<Recipe> recipes = queryType.getResultList();

        closeSession();
        return recipes;
    }

}
