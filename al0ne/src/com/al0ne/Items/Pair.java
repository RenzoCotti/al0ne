package com.al0ne.Items;

import com.al0ne.Entities.Entity;

/**
 * Created by BMW on 09/03/2017.
 */
public class Pair {
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

    public void modifyCount(Integer amount) {
        count+=amount;
    }

    public boolean subCount() {
        count--;
        if (count <= 0){
            return true;
        }
        return false;
    }
}
