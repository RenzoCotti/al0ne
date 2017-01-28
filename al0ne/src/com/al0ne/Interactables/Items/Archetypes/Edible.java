package com.al0ne.Interactables.Items.Archetypes;

/**
 * Created by BMW on 28/01/2017.
 */
public abstract class Edible extends Pickable {
    public Edible(String description, double weight) {
        super(description, weight);
        this.setUses("edible");
    }
}
