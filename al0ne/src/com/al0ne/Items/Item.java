package com.al0ne.Items;

import com.al0ne.Entities.Entity;
import com.al0ne.Entities.Player;
import com.al0ne.Room;

import java.util.ArrayList;

/**
 * Created by BMW on 02/02/2017.
 *
 * Item interface
 */
public abstract class Item extends Entity {

    protected double weight;
    protected ArrayList<String> properties;



    public Item(String id, String name, String description, String shortDescription, double weight) {
        super(id, name, description, shortDescription);
        this.weight = weight;
        this.properties = new ArrayList<>();
        addCommand("take");
        this.type='i';
    }

    public double getWeight() {
        return weight;
    }





    protected void addProperty(String behaviour){
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

}
