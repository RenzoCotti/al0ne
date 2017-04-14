package com.al0ne.Behaviours;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class Spell {

    protected char target;
    protected String id;
    protected String name;
    protected String description;

    public Spell(String id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public char getTarget() {
        return target;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean canCastOn(char c){
        return target == c;
    }
}
