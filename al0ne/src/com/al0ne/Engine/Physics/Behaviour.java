package com.al0ne.Engine.Physics;

import com.al0ne.Behaviours.abstractEntities.Entity;

import java.io.Serializable;

/**
 * Created by BMW on 09/07/2017.
 */
public abstract class Behaviour implements Serializable{
    public String name;
    public Entity toAdd;
    public int count;
    public String lock;


    public Behaviour(String s){
        this.name = s;
    }

    /*return codes:
    * 0: didn't work
    * 1: it worked, no print
    * 2: add entity to room
    * 3: add item to inventory
    * 4: remove this from where it was
    * 5: remove other from where it was
    * 6: unlock door
    * 7: ...
    *
    * */
    public abstract int isInteractedWith(Behaviour b);

    public String getName() {
        return name;
    }

    public Entity getToAdd() {
        return toAdd;
    }

    public void setToAdd(Entity toAdd, int count) {
        this.toAdd = toAdd;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }
}
