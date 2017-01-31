package com.al0ne.Interactables.Items.Archetypes;

import java.util.ArrayList;

/**
 * Created by BMW on 28/01/2017.
 */
public abstract class Pickable extends Interactable {

    protected double weight;
    protected ArrayList<String> uses;
    protected boolean toggled = false;

    public Pickable(String name, String description, double weight) {
        super(name, description);
        this.weight = weight;
        this.uses=new ArrayList<>();
    }

    @Override
    public void printDescription(){
        System.out.println(description+". Weighs "+weight+" kg");
    }

    public ArrayList<String> getUses() {
        return uses;
    }

    public void setUses(String use) {
        uses.add(use);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


}
