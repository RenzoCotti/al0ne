package com.al0ne.Engine.Physics;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.abstractEntities.Entity;

import java.io.Serializable;

/**
 * Created by BMW on 11/07/2017.
 */
public class AddEntity implements Serializable{
    protected boolean canAdd;
    protected Entity entity;
    protected int count;

    public AddEntity(){
        canAdd = false;
    }

    public AddEntity(Entity entity, int count){
        canAdd = true;
        this.entity = entity;
        this.count = count;
    }

    public Pair getPair(){
        if(canAdd){
            return new Pair(entity, count);
        }
        return null;
    }
    public boolean canAdd(){
        return canAdd;
    }
}
