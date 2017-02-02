package com.al0ne.Items;

import java.util.ArrayList;

/**
 * Created by BMW on 02/02/2017.
 *
 * Item interface
 */
public abstract class Item {

    protected String name;
    protected String description;
    protected double weight;

    protected ArrayList<String> properties;


    public Item(String name, String description, double weight) {
        this.name = name;
        this.weight = weight;
        this.description = description;
        this.properties= new ArrayList<>();
    }

    public void addProperty(String behaviour){
        properties.add(behaviour);
    }

    public boolean hasProperty(String property){
        for (String s : properties){
            if (s.equals(property)){
                return true;
            }
        }
        return false;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void printDescription() {
        System.out.println(description);
    }

    public double getWeight() {
        return weight;
    }
}
