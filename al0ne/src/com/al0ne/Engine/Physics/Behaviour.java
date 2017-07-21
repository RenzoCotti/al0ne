package com.al0ne.Engine.Physics;

import com.al0ne.Behaviours.abstractEntities.Entity;

import java.io.Serializable;

/**
 * Created by BMW on 09/07/2017.
 */
public abstract class Behaviour implements Serializable{
    public String name;
    protected AddEntity entity;


    public Behaviour(String s, AddEntity entity){
        if(entity == null){
            this.entity = new AddEntity();
        } else{
            this.entity = entity;
        }
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
    * 7: refill charge
    * 8: modify health
    * 9: modify integrity
    * */

    public String getName() {
        return name;
    }

    public AddEntity getEntity() {
        return entity;
    }

    public void setEntity(AddEntity entity) {
        this.entity = entity;
    }
}
