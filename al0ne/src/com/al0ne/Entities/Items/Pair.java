package com.al0ne.Entities.Items;

import com.al0ne.Behaviours.Entity;

import java.io.Serializable;

/**
 * Created by BMW on 09/03/2017.
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
