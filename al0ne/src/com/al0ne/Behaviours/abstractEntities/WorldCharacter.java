package com.al0ne.Behaviours.abstractEntities;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Status;
import com.al0ne.Entities.Items.Types.Wearable.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * placeholder class for attackable npcs
 */
public abstract class WorldCharacter extends Entity {



    //Maps ItemID, Pair
    protected HashMap<String, Pair> inventory;


    //current and max Health of the player
    protected int currentHealth;
    protected int maxHealth;

    //fighting stats
    protected int attack;
    protected int dexterity;

    //base armor
    protected int armor;
    protected int damage;

    //maps BodyPart to Item
    protected HashMap<String, Item> wornItems;

    //statuses
    protected HashMap<String, Status> status;

    //various
    protected boolean alive;

    private String causeOfDeath;


    public WorldCharacter(String id, String name, String longDescription, String shortDescription,
                          Integer maxHealth, Integer attack, Integer dexterity, Integer armor, Integer damage) {
        super(id, name, longDescription, shortDescription);
        this.inventory = new HashMap<>();
        this.wornItems = new HashMap<>();
        initialiseWorn();

        this.alive = true;
        if(maxHealth == null){
            this.maxHealth = 20;
            this.currentHealth = 20;
        } else{
            this.maxHealth = maxHealth;
            this.currentHealth = maxHealth;
        }

        if(attack == null){
            this.attack = 40;
        } else{
            this.attack = attack;
        }

        if(dexterity == null){
            this.dexterity = 40;
        } else{
            this.dexterity = dexterity;
        }

        if(armor == null){
            this.armor = 1;
        } else{
            this.armor = armor;
        }

        if(damage == null){
            this.damage = 2;
        } else{
            this.damage = damage;
        }

        this.status = new HashMap<>();
        this.causeOfDeath = "unknown causes";

    }

    //this function initialises the HashMap with all the body parts
    public void initialiseWorn(){
        wornItems.put("main hand", null);
        wornItems.put("off hand", null);
        wornItems.put("body", null);
        wornItems.put("head", null);
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
    public Item getOffHand(){
        return wornItems.get("off hand");
    }


    //returns true if the player is wearing the current item
    public boolean isWearingItem(String id){
        for (String part : wornItems.keySet()){
            Item currentItem = wornItems.get(part);
            if(currentItem != null) {
                if(id.equals(currentItem.getID())){
                    return true;
                }
            }

        }
        return false;
    }

    public HashMap<String, Pair> getInventory() {
        return inventory;
    }

    public HashMap<String, Item> getWornItems() {
        return wornItems;
    }

    //this function unequips the given item, if it's equipped
    public void unequipItem(String id){
        for (String part : wornItems.keySet()){
            Item currentItem = wornItems.get(part);
            if(currentItem != null) {
                if(id.equals(currentItem.getID())){
                    wornItems.put(part, null);
                }
            }

        }
    }


    //this function equips an item to the correct slot, if it's a wearable
    public boolean wear(Item wearable){
//        for (Pair pair : inventory.values()){
//            Item currentItem = (Item) pair.getEntity();
        if (wearable instanceof Armor || wearable instanceof Helmet || wearable instanceof Shield){
            String part = ((Wearable) wearable).getPart();
            if(part.equals("head")){
                wornItems.put(part, wearable);
            } else if(part.equals("body")){
                wornItems.put(part, wearable);
            }
            return true;
        } else {
            if (wearable instanceof Weapon){
                wornItems.put("main hand", wearable);
            } else {
                wornItems.put("off hand", wearable);
            }
            return true;
        }
//        }
    }


    public int getCurrentHealth() {
        return currentHealth;
    }

    //this returns true if the character is alive
    public boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    //this functions return the required value
    public int getAttack() {
        return attack;
    }
    public int getDexterity() {

        return dexterity;
    }
    public int getMaxHealth() {
        return maxHealth;
    }


    //this function sets health to the amount given
    //it binds it to maxHealth and 0
    public void setHealth(int amount) {
        if(currentHealth+amount > maxHealth){
            currentHealth=maxHealth;
        } else if(currentHealth+amount < 0){
            currentHealth=0;
            alive=false;
        }
    }

    //this function modifies health with the amount given
    //it binds it to maxHealth and 0
    //returns true if the player is still alive
    public boolean modifyHealth(int health) {
        if (this.currentHealth + health <= maxHealth){
            this.currentHealth += health;
            if (this.currentHealth<=0){
                this.currentHealth=0;
                alive = false;
                return false;
            }
            return true;
        } else{
            return true;
        }
    }

    //this function computes the total level of protection given by armor
    public int getArmorLevel(){
        Armor armor = getArmor();
        Helmet helmet = getHelmet();
        Item offHand = getOffHand();

        int armorLevel=this.armor;
        if(armor != null){
            armorLevel += armor.getArmor();
        }
        if(helmet != null){
            armorLevel += helmet.getArmor();
        }
        if(offHand != null && offHand instanceof Shield){
            armorLevel += ((Shield) offHand).getArmor();
        }

        return armorLevel;
    }

    public int getDamage(){
        int damage = this.damage;
        Wearable weapon = getWeapon();
        if(weapon != null){
            damage += ((Weapon) weapon).getDamage();
        }
        return damage;
    }


    //this function checks if the character has an item in the inventory
    //we check for ITEMID EQUALITY
    //if there is no item, it returns false
    public boolean hasItemInInventory(String item){
        for (String s : inventory.keySet()){
            if (s.equals(item)){
                return true;
            }
        }
        return false;
    }

    //this function tries to get an item from the inventory
    //if there is no such item, it returns null
    public Pair getItemPair(String itemID){
        if(hasItemInInventory(itemID)){
            return inventory.get(itemID);
        } else {
            return null;
        }
    }

    //returns the statuses
    public HashMap<String, Status> getStatus() {
        return status;
    }

    //adds a status to the statuses, it refreshes the duration
    public boolean addStatus(Status s) {
        for (Status st : status.values()){
            if(s.getName().equals(st.getName())){
                //refresh on reapply of status
                st.setDuration(s.getMaxDuration());
                return false;
            }
        }
        s.setDuration(s.getMaxDuration());
        status.put(s.getName(), s);
        return true;
    }

    //this removes a status if the name matches
    public boolean removeStatus(String statusName) {

        if (status.get(statusName) != null){
            status.remove(statusName);
        }
        return true;
    }

    //returns true if the player has the status s
    public boolean hasStatus(String s) {
        return status.get(s) != null;
    }

    public void handleStatuses(){
        if(status.size()>0){
            ArrayList<Status> toRemove = new ArrayList<>();
            ArrayList<Status> toAdd = new ArrayList<>();
            for (Status status: status.values()){
                if(status.resolveStatus(this)){
                    toAdd.addAll(status.getToApply());
                    toRemove.add(status);
                }
            }
            for (Status st : toRemove){
                status.remove(st.getName());
            }

            for (Status toApply : toAdd){
                status.put(toApply.getName(), toApply);
            }
        }
    }

    public boolean simpleAddItem(Item item, Integer amount){
        if (hasItemInInventory(item.getID())){
            Pair fromInventory = inventory.get(item.getID());
            fromInventory.modifyCount(amount);
            return true;
        } else {
            inventory.put(item.getID(), new Pair(item, amount));
            return true;
        }
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

}
