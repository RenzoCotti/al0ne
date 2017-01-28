package com.al0ne.Interactables.Items.Archetypes;

/**
 * Created by BMW on 28/01/2017.
 */
public abstract class Sharp extends Pickable {
    public Sharp(String name, String description, double weight) {
        super(name, description, weight);
        this.setUses("sharp");
    }
}
