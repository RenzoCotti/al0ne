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
