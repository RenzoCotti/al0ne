package com.al0ne.AbstractEntities.Pairs;

import java.util.ArrayList;

/**
 * Created by BMW on 08/05/2017.
 */
public class PotentialItems {
    private ArrayList<Pair> items;
    private int reliability;

    public PotentialItems(ArrayList<Pair> items, int reliability) {
        this.items = items;
        this.reliability = reliability;
    }

    public int getReliability() {
        return reliability;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    public ArrayList<Pair> getItems() {
        return items;
    }
}
