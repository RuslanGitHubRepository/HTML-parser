package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;

public abstract class DbQueryConstants {
    public static final String INSERT_INTO_TABLE_STORE = "INSERT INTO Ingredients(typeof, storeCount) VALUES (?, ?)";
    public static final String DROP_TABLE_STORE = "DROP TABLE IF EXISTS Ingredients";
    public static final String CREAT_TABLE_STORE = "CREATE TABLE IF NOT EXISTS Ingredients (id MEDIUMINT NOT NULL AUTO_INCREMENT, typeof VARCHAR(30) NOT NULL, storeCount MEDIUMINT, PRIMARY KEY (id))";
    public static final String GET_RECIPE_KITCHEN = "SELECT * FROM Recipes WHERE id = ?";
    public static final String INSERT_NEW_RECORD_KITCHEN = "INSERT INTO Recipes(id) VALUES (?)";
    public static final String GET_QUANTITY_STORE = "SELECT storeCount FROM Ingredients WHERE typeof = ?";
    public static final String GET_ALL_QUANTITY_STORE = "SELECT typeof FROM Ingredients";
    public static final String UPDATE_COUNTS_STORE = "UPDATE Ingredients SET storeCount = ? WHERE typeof = ?";
    public static String getUpdateCommand(String nameColumn) {
        return "UPDATE Recipes SET "+nameColumn+" = ? WHERE id = ?";
    }
}