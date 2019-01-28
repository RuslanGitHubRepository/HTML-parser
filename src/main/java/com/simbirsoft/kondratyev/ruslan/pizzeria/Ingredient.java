package com.simbirsoft.kondratyev.ruslan.pizzeria;

public class Ingredient {
    public Ingredient(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
    private String name;

}
