package com.simbirsoft.kondratyev.ruslan.pizzeria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    private static Session session;
    private static Transaction transaction;

    static {
        Configuration cfg = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure();
        sessionFactory = cfg.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void openSession(){
        session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();
    }
    public static void commitSession(){
        transaction.commit();
    }
    public static Session getSession(){
        return session;
    }
}