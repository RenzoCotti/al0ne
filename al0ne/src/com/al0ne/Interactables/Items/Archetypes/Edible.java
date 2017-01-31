package com.al0ne.Interactables.Items.Archetypes;

/**
 * Created by BMW on 28/01/2017.
 */
public abstract class Edible extends Consumable {
    public Edible(String name, String description, double weight) {
        super(name, description, weight);
        this.setUses("edible");
    }
}
