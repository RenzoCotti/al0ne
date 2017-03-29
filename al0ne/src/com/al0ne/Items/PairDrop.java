package com.al0ne.Items;

import com.al0ne.Entities.Entity;

import java.io.Serializable;

/**
 * Created by BMW on 09/03/2017.
 */
public class PairDrop extends Pair{
    private Entity entity;
    private int count;
    private int probability;

    public PairDrop(Entity entity, int count, int probability) {
        super(entity, count);
        this.entity = entity;
        this.count = count;
        this.probability = probability;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        count++;
    }

    public void setCount(Integer amount) {
        count=amount;
    }

    public boolean modifyCount(Integer amount) {
        count+=amount;
        if(count <= 0){
            return false;
        }
        return true;
    }

    public boolean subCount() {
        count--;
        if (count <= 0){
            return false;
        }
        return true;
    }

    public boolean isEmpty(){
        return count <= 0;
    }
}
