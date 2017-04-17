package com.al0ne.Behaviours.Pairs;

import com.al0ne.Behaviours.Entity;

/**
 * Created by BMW on 09/03/2017.
 */
public class PairDrop extends Pair {
    private int probability;

    public PairDrop(Entity entity, int count, int probability) {
        super(entity, count);
        this.probability = probability;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

}
