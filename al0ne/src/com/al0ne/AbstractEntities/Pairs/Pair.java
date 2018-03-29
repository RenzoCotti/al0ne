package com.al0ne.AbstractEntities.Pairs;

import com.al0ne.AbstractEntities.Abstract.Entity;

import java.io.Serializable;

/**
 * This represent a quantity of an item/npc/enemy...
 * entity represents the object
 * count the quantity
 * location represents where it is located:
 * c: container
 * r: room
 * i: inventory
 */
public class Pair implements Serializable{
    private Entity entity;
    private int count;
    //r: in room
    //c: in container
    //i: in inventory
    private char location;


    public Pair(Entity entity, int count) {
        this.entity = entity;
        this.count = count;
        this.location = 'r';
    }

    public char getLocation() {
        return location;
    }

    public void setLocation(char location) {
        this.location = location;
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

    public boolean setCount(Integer amount) {
        count=amount;
        if(count <= 0) {
            return true;
        }
        return false;
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
