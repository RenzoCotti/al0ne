package com.al0ne.ConcreteEntities.Items.Types;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;

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
    protected boolean locked;

    public Container(String id, String name, String description, double weight, Size size,
                     Material material, boolean canAdd) {
        super(id, name, description, weight, size, material, null);
        this.canAdd = canAdd;
        this.currentWeight = 0;
        this.currentSize = 0;
        this.type='C';
        this.items = new ArrayList<>();
        this.locked = false;
    }

    public Container(String name, String description, Size size, Material material) {
        super(name, name, description, 10000, size, material, null);
        this.canAdd = false;
        this.currentWeight = 0;
        this.currentSize = 0;
        this.type='C';
        this.items = new ArrayList<>();
        this.locked = false;

    }

    public Container(String name, String description, Size size, Material material, boolean locked) {
        super(name, name, description, 10000, size, material, null);
        this.canAdd = false;
        this.currentWeight = 0;
        this.currentSize = 0;
        this.type='C';
        this.items = new ArrayList<>();
        this.locked = locked;

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

    @Override
    public double getWeight() {
        return weight+currentWeight;
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

    //return true/false if successful
    public boolean putIn(Pair pair, Integer amount){

        Item item = (Item) pair.getEntity();
        if (canAdd && (item.getSize())*amount+currentSize < size){
            if(hasItem(item)){
                getPair(item).modifyCount(amount);
                pair.modifyCount(-amount);
                currentSize+=item.getSize()*amount;
                currentWeight+=item.getWeight()*amount;
            } else{
                items.add(new Pair(item, amount));
                pair.modifyCount(-amount);
                currentSize+=item.getSize()*amount;
                currentWeight+=item.getWeight();
            }
            return true;
        } else{
            return false;
        }
    }

    public void removeItem(Pair pair){
        Item item = (Item) pair.getEntity();
        weight-=item.getWeight();
        if(weight<= 0) weight=0;
        currentSize-=item.getSize();
        items.remove(pair);
    }

    public ArrayList<Pair> getItems() {
        return items;
    }

    public boolean canAdd() {
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

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        this.locked = false;
    }

    public void lock(){
        this.locked = true;
    }

    @Override
    public void printLongDescription(Player player) {
        if (items.size() == 0) {
            printToLog(longDescription);
            return;
        }
        printToLog(longDescription);
        if(locked) {
            printToLog("It's locked.");
            return;
        }
        printToLog("It contains: ");
        for (Pair pair: items){
            Item item = (Item) pair.getEntity();
            if ( pair.getCount() == 1 ){
                printToLog(item.getShortDescription());
            } else{
                printToLog(pair.getCount()+"  "+item.getName());
            }
        }
    }
}
