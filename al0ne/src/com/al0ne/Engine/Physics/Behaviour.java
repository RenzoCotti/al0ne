package com.al0ne.Engine.Physics;

import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Abstract.Entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BMW on 09/07/2017.
 */
public abstract class Behaviour implements Serializable{
    public String name;
    protected ArrayList<Pair> toAdd;


    public Behaviour(String s){
        this.toAdd = new ArrayList<>();
        this.name = s;
    }


    public String getName() {
        return name;
    }

    public ArrayList<Pair> getToAdd() {
        return toAdd;
    }

    public void addEntity(Entity entity, int count) {
        this.toAdd.add(new Pair(entity, count));
    }
}
