package com.al0ne.ConcreteEntities.Spells;

import com.al0ne.AbstractEntities.Abstract.Entity;

import java.io.Serializable;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class Spell implements Serializable{

    protected Class target;
    protected String id;
    protected String name;
    protected String description;

    public Spell(String id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Class getTarget() {
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

    public boolean canCastOn(Entity e){
        return e.getClass().equals(target);
    }
}
