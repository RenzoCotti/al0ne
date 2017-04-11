package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Item;
import com.al0ne.Entities.Items.Pair;

import java.util.ArrayList;

/**
 * Created by BMW on 11/04/2017.
 */
public class Container extends Item{

    protected ArrayList<Pair> items;
    protected boolean canAdd;
    protected int maxSize;
    protected int currentSize;
    protected double currentWeight;

    public Container(String id, String name, String description, String shortDescription, double weight, boolean canAdd, Size maxSize) {
        super(id, name, description, shortDescription, weight);
        this.canAdd = canAdd;
        this.maxSize = Size.toInt(maxSize);
        this.currentWeight = 0;
        this.currentSize = 0;
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }

    public boolean hasItem(Item item){
        return false;
    }

    public boolean putIn(Item item){
        return false;
    }

    public boolean takeFrom(Item item){
        return false;
    }
}
