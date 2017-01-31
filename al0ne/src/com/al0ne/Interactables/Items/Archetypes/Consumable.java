package com.al0ne.Interactables.Items.Archetypes;

/**
 * Created by BMW on 31/01/2017.
 */
public class Consumable extends Pickable{

    public Consumable(String name, String description, double weight) {
        super(name, description, weight);
    }

    public void use(){
        toggle();
        System.out.println("You consume the "+getName());
    }
}
