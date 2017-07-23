package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.abstractEntities.Enemy;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Behaviours.abstractEntities.Interactable;
import com.al0ne.Behaviours.abstractEntities.WorldCharacter;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Container;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;
import com.al0ne.Entities.Items.Behaviours.Wearable.*;
import com.al0ne.Entities.Items.ConcreteItems.Coin.BrassCoin;
import com.al0ne.Entities.Items.ConcreteItems.Coin.GoldCoin;
import com.al0ne.Entities.Items.ConcreteItems.Coin.SilverCoin;
import com.al0ne.Entities.Statuses.ConcreteStatuses.Hunger;
import com.al0ne.Entities.Statuses.ConcreteStatuses.NaturalHealing;
import com.al0ne.Entities.Statuses.ConcreteStatuses.Thirst;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;
import static com.al0ne.Engine.Main.printToSingleLine;

/**
 * a Player is:
 * an inventory, storing itemID and ConcreteItems
 * a currentRoom, a Room the player is currently in
 * a maxWeight, double, representing the max carry weight
 * a currentWeight, double
 * a maxHealth, a double representing the max health
 * a currentHealth
 * an attack, int, representing how likely the player is to hit
 * a dexterity, int, representing the player's dodging chance
 * a status, a HashMap <StatusID, Status>
 * a toApply, a queue of status that will be applied at the end of the turn
 * an alive, boolean. Represents if the player is alive or not
 * a hasNeeds, boolean: represents if the current Player will become hungry/thirsty
 * a story, String containing a brief introduction about the player
 * a quests, HashMap<questID, Boolean>, representing all due quests
 * a wornItems, HashMap<BodyPart, Item>: all equipped items
 */
public class Player extends WorldCharacter {

    //stores current room the player is in
    private Room currentRoom;
    
    //Maximum carry weight of the player
    private double maxWeight;
    //Current carry weight of the player
    private double currentWeight;

    //various
    private boolean hasNeeds;

    //maps questID to boolean
    private HashMap<String, Boolean> quests;

    //maps BodyPart to Item
    private HashMap<String, Wearable> wornItems;

    private String causeOfDeath;
    
    // add also money pouch?


    //creates a new Player, sets the current Room to currentRoom
    //inventory is empty, weight is 0
    //add thirst and hunger if needs is true
    public Player(boolean needs, double maxWeight, Room currentRoom, String story) {
        super("alpha", "player", story, "da",
        10, 70, 30, 0, 1);
        this.currentRoom = currentRoom;
        this.maxWeight = maxWeight;
        this.currentWeight=0;
        this.wornItems = new HashMap<>();
        this.quests = new HashMap<>();
        this.causeOfDeath = "unknown causes";
        initialiseWorn();
        if(needs){
            putStatus(new Thirst());
            putStatus(new Hunger());
        }
        this.hasNeeds = needs;
        putStatus(new NaturalHealing());
    }

    //creates a new Player, sets the current Room to currentRoom
    //inventory is empty, weight is 0
    //add thirst and hunger if needs is true
    public Player( String name, String story, boolean needs, Room currentRoom,
                  int maxHealth, int attack, int dexterity, int armor, int damage, double maxWeight ) {
        super("alpha", name, story, "da",
                maxHealth, attack, dexterity, armor, damage);
        this.currentRoom = currentRoom;
        this.maxWeight = maxWeight;
        this.currentWeight=0;
        this.wornItems = new HashMap<>();
        this.quests = new HashMap<>();
        this.causeOfDeath = "unknown causes";
        setLongDescription("You are "+name+".\n"+story);
        initialiseWorn();
        if(needs){
            putStatus(new Thirst());
            putStatus(new Hunger());
        }
        this.hasNeeds = needs;
        putStatus(new NaturalHealing());
    }

    //this function initialises the HashMap with all the body parts
    public void initialiseWorn(){
        wornItems.put("main hand", null);
        wornItems.put("off hand", null);
        wornItems.put("body", null);
        wornItems.put("head", null);
    }

    //returns true if the player has basic needs
    public boolean hasNeeds() {
        return hasNeeds;
    }

    //debug function to kill the player
    public void killPlayer(){
        this.alive = false;
    }

    //returns the currently equipped X or null
    public Weapon getWeapon(){
        return (Weapon) wornItems.get("main hand");
    }
    public Armor getArmor(){
        return (Armor) wornItems.get("body");
    }
    public Helmet getHelmet(){
        return (Helmet) wornItems.get("head");
    }
    public Wearable getOffHand(){
        return wornItems.get("off hand");
    }


    //returns true if the player is wearing the current item
    public boolean isWearingItem(String id){
        for (String part : wornItems.keySet()){
            Wearable currentItem = wornItems.get(part);
            if(currentItem != null) {
                if(id.equals(currentItem.getID())){
                    return true;
                }
            }

        }
        return false;
    }

    //this function unequips the given item, if it's equipped
    public void unequipItem(String id){
        for (String part : wornItems.keySet()){
            Wearable currentItem = wornItems.get(part);
            if(currentItem != null) {
                if(id.equals(currentItem.getID())){
                    wornItems.put(part, null);
                }
            }

        }
    }

    //this function sets currentWeight to the given weight
    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    //this function equips an item to the correct slot, if it's a wearable
    public boolean wear(Item wearable){
//        for (Pair pair : inventory.values()){
//            Item currentItem = (Item) pair.getEntity();
            if (wearable.getType() == 'w'){
                String part = ((Wearable) wearable).getPart();
                if (part.equals("main hand")){
                    wornItems.put(part, (Weapon) wearable);
                    printToLog("You now wield the "+wearable.getName());
                } else if(part.equals("off hand")){
                    wornItems.put(part, (Wearable) wearable);
                    printToLog("You now wear the "+wearable.getName());
                } else if(part.equals("head")){
                    wornItems.put(part, (Helmet) wearable);
                    printToLog("You now wear the "+wearable.getName());
                } else if(part.equals("body")){
                    wornItems.put(part, (Armor) wearable);
                    printToLog("You now wear the "+wearable.getName());
                }
                return true;
            }
//        }
        return false;

    }

    //this function prints the currently equipped weapon
    public void printWielded(){
        if(getWeapon() == null){
            printToLog("You're using your fists");
            return;
        }
        printToLog("You're using your "+getWeapon().getName());
    }

    //this function prints all currently equipped armor pieces
    public void printArmor(){

        boolean first=true;
        for (Wearable w : wornItems.values()){
            if (w != null && !(w instanceof Weapon)){
                if(first){
                    printToSingleLine("You're wearing "+w.getShortDescription());
                    first = false;
                }else {
                    printToSingleLine(", "+w.getShortDescription());
                }
            }
        }
        if(first){
            printToLog("You're not wearing anything.");
        } else{
            printToLog();
        }
    }

    //this function computes the total level of protection given by armor
    @Override
    public int getArmorLevel(){
        Armor armor = getArmor();
        Helmet helmet = getHelmet();
        Wearable offHand = getOffHand();

        int armorLevel=this.armor;
        if(armor != null){
            armorLevel= armor.getArmor();
        }
        if(helmet != null){
            armorLevel= helmet.getArmor();
        }
        if(offHand != null && offHand instanceof Shield){
            armorLevel= ((Shield) offHand).getArmor();
        }

        return armorLevel;
    }

    @Override
    public int getDamage(){
        int damage = this.damage;
        Wearable weapon = getWeapon();
        if(weapon != null){
            damage+=((Weapon) weapon).getDamage();
        }
        return damage;
    }

    public int getEncumberment(){
        Armor armor = getArmor();
        Helmet helmet = getHelmet();
        Wearable offHand = getOffHand();

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

    //this function prints a string corresponding to the current
    //health level
    public void printHealthStatus(){
        double percentage = ((double)currentHealth/(double)maxHealth)*100;

        if (percentage >= 80){
            printToLog("You're as healthy as ever.");
        } else if (percentage >= 60 && percentage < 80){
            printToLog("You're mostly fine.");
        } else if (percentage >= 40 && percentage < 60){
            printToLog("You need to medicate.");
        } else if (percentage >= 20 && percentage < 40){
            printToLog("You're bleeding heavily");
        } else {
            if (this.currentHealth<=0){
                return;
            }
            printToLog("You're alive by a miracle");
        }
    }


    //gets the requested resource
    public double getMaxWeight() {
        return maxWeight;
    }
    public double getCurrentWeight() {
        return currentWeight;
    }

    //debug function, prints the current weight.
    public void printWeight() {
        printToLog(currentWeight+"/"+maxWeight+" kg.");
    }

    //this function modifies the current weight
    //it rounds off the result correctly
    //the weight is bound by maxWeight and 0
    public boolean modifyWeight(double weight) {
        if (this.currentWeight+weight <= maxWeight){
            this.currentWeight+=weight;
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

    //prints the inventory
    public void printInventory(){
        if (inventory.size()==0){
            printToLog("You have no items.");
        } else {
            printToLog("You have these items:");
            for (Pair pair : inventory.values()) {
                Item currentItem = (Item) pair.getEntity();
                double weight = Utility.twoDecimals(currentItem.getWeight()*pair.getCount());
                printToLog("- "+pair.getCount()+"x " + currentItem.getName()+". "+weight+" kg.");
            }
            printToLog();
            printWeight();
        }
    }

    //this function removes 1 item from pair to the inventory
    public boolean removeOneItem(Item i) {
        Pair p = getItemPair(i.getID());
        if(p != null){
            modifyWeight(-i.getWeight());
            if(!p.subCount()){
                inventory.remove(i.getID());
            }
        }
        return true;
    }

    //this function removes 1 item from pair to the inventory
    public boolean removeXItem(Item i, int count) {
        Pair p = getItemPair(i.getID());
        if(p != null){
            modifyWeight(-i.getWeight() * count);
            if(!p.modifyCount(-count)){
                inventory.remove(i.getID());
            }
        }
        return true;
    }


            //this function adds 1 item from pair to the inventory
    //returns true if it's successful, else the player can't carry it
    public boolean addOneItem(Pair pair) {
        Item item = (Item) pair.getEntity();
        if (modifyWeight(item.getWeight())){
            if (hasItemInInventory(item.getID())){
                Pair fromInventory = inventory.get(item.getID());
                fromInventory.addCount();
                pair.subCount();
                return true;
            } else {
                inventory.put(item.getID(), new Pair(item, 1));
                pair.subCount();
                return true;
            }
        } else {
            return false;
        }
    }

    //this function adds X items from pair to the inventory
    //returns true if it's successful, else the player can't carry them
    public boolean addAmountItem(Pair pair, Integer amount) {
        Item item = (Item) pair.getEntity();
        if (modifyWeight(item.getWeight() * amount)){
            if (hasItemInInventory(item.getID())){
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

    //this function adds all items from pair to the inventory
    //returns true if it's successful, else the player can't carry them
    public boolean addAllItem(Pair pair) {
        Item item = (Item) pair.getEntity();
        if (modifyWeight(item.getWeight() * pair.getCount())){
            if (hasItemInInventory(item.getID())){
                Pair fromInventory = inventory.get(item.getID());
                fromInventory.modifyCount(pair.getCount());
                pair.modifyCount(-pair.getCount());
                return true;
            } else {
                inventory.put(item.getID(), new Pair(item, pair.getCount()));
                pair.modifyCount(-pair.getCount());
                return true;
            }
        } else {
            return false;
        }
    }

    //this function adds an item, amount times
    //returns true if it's successful, else the player can't carry it
    @Override
    public boolean simpleAddItem(Item item, Integer amount) {
        if (modifyWeight(item.getWeight() * amount)){
            return super.simpleAddItem(item, amount);
        } else {
            return false;
        }
    }



    //this function tries to get an item from the inventory
    //if there is no such item, it returns null
    public ArrayList<Pair> getByType(char type){
        ArrayList<Pair> listOfItems = new ArrayList<>();
        for(Pair p: inventory.values()){
            Item currentItem = (Item) p.getEntity();
            if(currentItem.getType() == type){
                listOfItems.add(p);
            }
        }
        return listOfItems;
    }

    //getter for currentRoom
    public Room getCurrentRoom() {
        return currentRoom;
    }

    //setter for currentRoom
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }






    //we check if the player has at least amt money
    public boolean hasEnoughMoney(int amt){
        return getMoney() >= amt;
    }

    //we return the money of the player
    public int getMoney(){
        Pair gold = inventory.get("gcoin");
        Pair silver = inventory.get("scoin");
        Pair brass = inventory.get("bcoin");

        int value=0;
        if(gold != null){
            value += gold.getCount()*100;
        }
        if(silver != null){
            value += silver.getCount()*10;
        }
        if(brass != null){
            value += brass.getCount();
        }
        return value;
    }

    //we remove amt money from the player
    public boolean removeAmountMoney(int amt){
        Pair gold = inventory.get("gcoin");
        Pair silver = inventory.get("scoin");
        Pair brass = inventory.get("bcoin");

        int totalMoney = getMoney();
        if(totalMoney >= amt){

            totalMoney-=amt;

            Pair tempGold = new Pair(new GoldCoin(), 0);
            Pair tempSilver = new Pair(new SilverCoin(), 0);
            Pair tempBrass = new Pair(new BrassCoin(), 0);
            ArrayList<Pair> values = new ArrayList<>();
            values.add(tempGold);
            values.add(tempSilver);
            values.add(tempBrass);

            while (totalMoney != 0){
                if(!(totalMoney % 10 == 0)){
                    //we subtract brass
                    values.get(2).addCount();
                    totalMoney--;
                } else if(!(totalMoney % 100 == 0)){
                    //we subtract silver
                    values.get(1).addCount();
                    totalMoney-=10;
                } else{
                    //we subtract gold
                    values.get(0).addCount();
                    totalMoney-=100;
                }
            }

            int i = 0;
            for (Pair p : values){
                Pair money;
                if(i==0){
                    money=gold;
                } else if(i==1){
                    money=silver;
                } else{
                    money=brass;
                }

                if(money != null){
                    if(money.setCount(p.getCount())){
                        inventory.remove(money.getEntity().getID());
                    }
                } else if(p.getCount() > 0){
                    addAllItem(p);
                }
                i++;
            }

            return true;

        }else {
            return false;
        }
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
//            System.out.println("Item: "+i.getName()+" "+i.getIntegrity()+"%");
            if(i.getIntegrity() <= 0 ){
                toRemove.add(i);
            }
        }
//        System.out.println("N. items to remove: "+toRemove.size());

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

    //returns the quests
    public HashMap<String, Boolean> getQuests() {
        return quests;
    }

    //this adds a quest to the quest log
    public void addQuest(String s) {
        this.quests.put(s, false);
    }

    public void completeQuest(String s) {
        if(this.quests.get(s) != null){
            this.quests.put(s, true);
        }
    }

    //returns true if the player has finished the quest
    public boolean hasDoneQuest(String s) {
        if(this.quests.get(s) != null){
            return this.quests.get(s);
        }
        return false;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }
}