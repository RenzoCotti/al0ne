package com.al0ne.AbstractEntities.Player;

import com.al0ne.AbstractEntities.Area;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Quests.Quest;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Abstract.WorldCharacter;
import com.al0ne.AbstractEntities.World;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.Currency;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.MoneyContainer;
import com.al0ne.ConcreteEntities.Items.Types.Container;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.*;
import com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses.Hunger;
import com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses.NaturalHealing;
import com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses.Thirst;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * a Player is:
 * a currentRoom, a Room the player is currently in
 * a currentArea, an Area representing the area the player's currently in
 * a maxWeight, double, representing the max carry weight
 * a currentWeight, double
 * a hasNeeds, boolean: represents if the current Player will become hungry/thirsty
 * a story, String containing a brief introduction about the player
 */
public class Player extends WorldCharacter {

    //stores current room the player is in
    private Room currentRoom;

    private Area currentArea;
    
    //Maximum carry weight of the player
    private double maxWeight;
    //Current carry weight of the player
    private double currentWeight;

    //various
    private boolean hasNeeds;



    //creates a new Player, sets the current Room to currentRoom
    //inventory is empty, weight is 0
    //add thirst and hunger if needs is true
    public Player(String story, boolean needs, int maxHealth, int attack,
                  int dexterity, int armor, int damage, double maxWeight ) {
        super("player", "player", story, "-",
                maxHealth, attack, dexterity, armor, damage);
        this.maxWeight = maxWeight;
        this.currentWeight=0;
        setLongDescription(story);
        if(needs){
            addStatus(new Thirst());
            addStatus(new Hunger());
        }
        this.hasNeeds = needs;
        addStatus(new NaturalHealing());
    }


    public Area getCurrentArea() {
        return currentArea;
    }

    public World getCurrentWorld(){
        return currentArea.getParentWorld();
    }

    public void setStart(Area currentArea) {
        this.currentArea = currentArea;
        this.currentRoom = currentArea.getStartingRoom();
    }

    public void setCurrentArea(Area currentArea) {
        this.currentArea = currentArea;
    }

    //returns true if the player has basic needs
    public boolean hasNeeds() {
        return hasNeeds;
    }

    //debug function to kill the player
    public void killPlayer(){
        this.alive = false;
    }


    //this function equips an item to the correct slot, if it's a wearable
    @Override
    public boolean wear(Item wearable){
        if(super.wear(wearable)){
            printToLog("You equipped the "+wearable.getName());
            return true;
        } else{
            return false;
        }

    }


    public int getEncumberment(){
        Armor armor = getArmor();
        Helmet helmet = getHelmet();
        Item offHand = getOffHand();

        int encumberment=0;
        if(armor != null){
            encumberment= armor.getEncumberment();
        }
        if(helmet != null){
            encumberment= helmet.getEncumberment();
        }
        if(offHand != null && offHand instanceof Shield){
            encumberment= ((Shield) offHand).getEncumberment();
        }

        return encumberment;
    }

    @Override
    public int getDexterity(){
        return dexterity-getEncumberment();
    }

    //gets the requested resource
    public double getMaxWeight() {
        return maxWeight;
    }
    public double getCurrentWeight() {
        return currentWeight;
    }



    //this function modifies the current weight
    //it rounds off the result correctly
    //the weight is bound by maxWeight and 0
    public boolean modifyWeight(double amt) {
        if (this.currentWeight+amt <= maxWeight){
            this.currentWeight+=amt;
            this.currentWeight= Utility.twoDecimals(currentWeight);

            if (currentWeight < 0){
                currentWeight=0;
            }
            return true;
        }

        return false;
    }


    //returns the inventory hashmap
    public HashMap<String, Pair> getInventory() {
        return inventory;
    }



    //this function removes 1 item from pair to the inventory
    public boolean removeOneItem(Item i) {
        return removeAmountItem(i, 1);
    }

    //this function removes 1 item from pair to the inventory
    public boolean removeAmountItem(Item i, int count) {
        Pair p = getItemPair(i.getID());
        if(p != null){
            modifyWeight(-i.getWeight() * count);
            //if the item is depleted, remove it
            if(!p.modifyCount(-count)){
                inventory.remove(i.getID());
            }
        }
        return true;
    }



    //this function adds X items from pair to the inventory
    //returns true if it's successful, else the player can't carry them
    public boolean addAmountItem(Pair pair, Integer amount) {
        Item item = (Item) pair.getEntity();
        if (modifyWeight(item.getWeight() * amount)){

            //case we're picking up money, add it to the money container
            if(pair.getEntity() instanceof Currency) {
                if(getMoneyContainer() != null){
                    getMoneyContainer().putIn(pair, amount);
                    return true;
                } else {
                    printToLog("You need a way to keep that money with you!");
                    return false;
                }
            } else if (hasItemInInventory(item.getID())){
                Pair fromInventory = inventory.get(item.getID());
                fromInventory.modifyCount(amount);
                pair.modifyCount(-amount);
                return true;
            } else {
                inventory.put(item.getID(), new Pair(item, amount));
                pair.modifyCount(-amount);
                return true;
            }
        } else {
            return false;
        }
    }

    //this function adds 1 item from pair to the inventory
    //returns true if it's successful, else the player can't carry it
    public boolean addOneItem(Pair pair) {
        return addAmountItem(pair, 1);
    }


    //this function adds all items from pair to the inventory
    //returns true if it's successful, else the player can't carry them
    public boolean addAllItem(Pair pair) {
        return addAmountItem(pair, pair.getCount());
    }

    //this function adds an item, amount times
    //returns true if it's successful, else the player can't carry it
    @Override
    public boolean simpleAddItem(Item item, Integer amount) {
        return modifyWeight(item.getWeight() * amount) && super.simpleAddItem(item, amount);
    }



    //getter for currentRoom
    public Room getCurrentRoom() {
        return currentRoom;
    }

    //setter for currentRoom
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }




    //this recalculates weight, run at the end of every turn
    public void recalculateWeight(){
        double amt = 0;
        for(Pair p : inventory.values()){
            amt+=((Item)p.getEntity()).getWeight();
        }
        currentWeight = Utility.twoDecimals(amt);
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void removeIfDestroyed(){
        ArrayList<Item> toRemove = new ArrayList<>();
        for(Pair p : inventory.values()){
            Item i = (Item) p.getEntity();
            if(i.getIntegrity() <= 0 ){
                toRemove.add(i);
            }
        }

        //removes all items, even if there are multiple copies of different integrity
        for(Item i : toRemove){
            if(isWearingItem(i.getID())){
                unequipItem(i.getID());
            }
            printToLog("Your "+ i.getName()+ " is destroyed!");
            inventory.remove(i.getID());
        }
    }



    //returns the story of the player
    public String getStory() {
        return getLongDescription();
    }



    public HashMap<String, Quest> getQuests() {
        return getCurrentArea().getParentWorld().getQuests();
    }


    public MoneyContainer getMoneyContainer(){
        HashMap<String, Pair> inventory = getInventory();

        for(Pair p : inventory.values()){
            Item i = (Item) p.getEntity();

            if(i instanceof MoneyContainer){
                return (MoneyContainer) i;
            }
        }

        return null;
    }

    public ArrayList<Container> getContainers(){
        HashMap<String, Pair> inventory = getInventory();
        ArrayList<Container> result = new ArrayList<>();

        for(Pair p : inventory.values()){
            Item i = (Item) p.getEntity();

            if(i instanceof Container){
                result.add((Container) i);
            }
        }

        return result;

    }


}