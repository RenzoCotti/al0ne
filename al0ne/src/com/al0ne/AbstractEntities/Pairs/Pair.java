package com.al0ne.AbstractEntities.Pairs;

import com.al0ne.AbstractEntities.Abstract.Entity;

import java.io.Serializable;

/**
 * This represent a quantity of an item/npc/enemy...
 * entity represents the object
 * count the quantity
 */
public class Pair implements Serializable{
    private Entity entity;
    private int count;

    public Pair(Entity entity, int count) {
        this.entity = entity;
        this.count = count;
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

    public boolean modifyCount(Integer amount) {
        count+=amount;
        if(count <= 0){
            return false;
        }
        return true;
    }

    public boolean isEmpty(){
        return count <= 0;
    }
}
