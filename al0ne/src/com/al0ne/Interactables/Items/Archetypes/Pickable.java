package com.al0ne.Interactables.Items.Archetypes;

import java.util.ArrayList;

/**
 * Created by BMW on 28/01/2017.
 */
public abstract class Pickable implements Interactable {

    private String description;
    private double weight;
    private ArrayList<String> uses;
    private boolean toggled = false;

    public Pickable(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }

    @Override
    public void isToggled(Pickable item) {
        toggled=true;
    }

    @Override
    public void setToggled(boolean toggled) {
        this.toggled=toggled;
    }

    @Override
    public boolean getToggled() {
        return toggled;
    }

    public ArrayList<String> getUses() {
        return uses;
    }

    public void setUses(String use) {
        this.uses.add(use);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
