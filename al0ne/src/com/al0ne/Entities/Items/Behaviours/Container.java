package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pair;

import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 11/04/2017.
 */
public class Container extends Item{

    protected ArrayList<Pair> items;
    protected boolean canAdd;
    protected int currentSize;
    protected double currentWeight;

    public Container(String id, String name, String description, String shortDescription, double weight, Size size, boolean canAdd) {
        super(id, name, description, shortDescription, weight, size);
        this.canAdd = canAdd;
        this.currentWeight = 0;
        this.currentSize = 0;
        this.type='C';
        this.items = new ArrayList<>();
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
    }

    public boolean hasItem(Item item){
        for (Pair p : items){
            Item currentItem = (Item) p.getEntity();
            if(item.getID().equals(currentItem.getID())){
                return true;
            }
        }
        return false;
    }

    public Pair getPair(Item item){
        for (Pair p : items){
            Item currentItem = (Item) p.getEntity();
            if(item.getID().equals(currentItem.getID())){
                return p;
            }
        }
        return null;
    }

    public boolean putIn(Player player, Item item){
        if (canAdd && item.getSize()+currentSize < size){
            if(hasItem(item)){
                getPair(item).addCount();
                currentSize+=item.getSize();
            } else {
                items.add(new Pair(item, 1));
                currentSize+=item.getSize();
            }
            return true;
        } else{
            return false;
        }
    }

    public boolean putIn(Player player, Item item, Integer amount){
        if (canAdd && (item.getSize())*amount+currentSize < size){
            if(hasItem(item)){
                getPair(item).modifyCount(amount);
                currentSize+=item.getSize()*amount;
            } else{
                items.add(new Pair(item, amount));
                currentSize+=item.getSize()*amount;
            }
            return true;
        } else{
            return false;
        }
    }

    public void removeItem(Pair pair){
        Item item = (Item) pair.getEntity();
        weight-=item.getWeight();
        currentSize-=item.getSize();
        items.remove(pair);
    }

    public ArrayList<Pair> getItems() {
        return items;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void modifySize(Integer value){
        currentSize+=value;
    }


    public void addItem(Item item){
        items.add(new Pair(item, 1));
    }

    public void addItem(Item item, Integer value){
        items.add(new Pair(item, value));
    }

    @Override
    public void printLongDescription(Player player, Room room) {
        printToLog(longDescription+". It contains: ");
        for (Pair pair: items){
            Item item = (Item) pair.getEntity();
            if ( pair.getCount() == 1 ){
                printToLog(item.getShortDescription());
            } else{
                printToLog(pair.getCount()+" x "+item.getName());
            }
        }
    }
}
