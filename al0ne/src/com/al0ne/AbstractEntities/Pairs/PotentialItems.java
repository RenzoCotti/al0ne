package com.al0ne.AbstractEntities.Pairs;

import com.al0ne.AbstractEntities.Abstract.Item;

import java.util.ArrayList;

/**
 * Created by BMW on 08/05/2017.
 * it's a container for all pairs found by the string matcher method
 * reliability is a counter for the best match found so far
 */
public class PotentialItems {
    private ArrayList<Pair> entities;
    private int reliability;

    public PotentialItems(ArrayList<Pair> entities, int reliability) {
        this.entities = entities;
        this.reliability = reliability;
    }

    public int getReliability() {
        return reliability;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    public ArrayList<Pair> getEntities() {
        return entities;
    }
    public ArrayList<Pair> getItems(){
        ArrayList<Pair> items = new ArrayList<>();
        for(Pair p: entities){
            if(p.getEntity() instanceof Item){
                items.add(p);
            }
        }
        return items;
    }
}
