package com.al0ne.Entities;

import com.al0ne.Items.Behaviours.Weapon;
import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
import com.al0ne.Items.Prop;
import com.al0ne.Room;

import java.util.HashMap;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Player is:
 * an inventory, storing itemID and Items
 * a currentRoom, a Room
 * a maxWeight, double
 * a currentWeight, double
 *
 * TODO probably add currentHealth, belly
 */
public class Player {

    //Maps ItemID, Item
    private HashMap<String, Pair> inventory;
    private Room currentRoom;
    
    //Maximum carry weight of the player
    private static double maxWeight=10;
    //Current carry weight of the player
    private double currentWeight;

    private int currentHealth =10;
    private static int maxHealth=10;
    private boolean alive = true;



//    private int money;

    private Weapon wieldedWeapon;
    
    // TODO: 08/03/2017 add currentHealth, satiation

    //creates a new Player, sets the current Room to currentRoom
    //inventory is empty, weight is 0
    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.inventory = new HashMap<>();
        this.currentWeight=0;
        this.wieldedWeapon = null;
    }


    public boolean wield(Item weapon){
        for (Pair pair : inventory.values()){
            Item currentItem = (Item) pair.getEntity();

            if (weapon.getID().equals(currentItem.getID()) && currentItem instanceof Weapon){
                wieldedWeapon = (Weapon) weapon;
                System.out.println("You now wield the "+weapon.getName());
                return true;
            }
        }
        return false;
    }

    public void printWielded(){
        if(wieldedWeapon == null){
            System.out.println("You're using your fists");
            return;
        }

        System.out.println("You're using your "+wieldedWeapon.getName());
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void printHealth() {
        System.out.println("You have "+ currentHealth +"/"+maxHealth+" HP.");
    }

    public void setHealth(int amount) {
        if(currentHealth+amount > maxHealth){
            currentHealth=maxHealth;
        } else if(currentHealth+amount < 0){
            currentHealth=0;
            alive=false;
        }
    }


    public void modifyHealth(int health) {
        if (this.currentHealth +health <= maxHealth){
            this.currentHealth +=health;
        }

        double percentage = ((double)currentHealth/(double)maxHealth)*100;

        if (percentage >= 80){
            System.out.println("You're mostly fine.");
        } else if (percentage >= 60 && percentage < 80){
            System.out.println("You've taken a good beating.");
        } else if (percentage >= 40 && percentage < 60){
            System.out.println("You need to medicate.");
        } else if (percentage >= 20 && percentage < 40){
            System.out.println("You're bleeding heavily");
        } else {
            if (this.currentHealth <= 0){
                alive=false;
            } else {
                System.out.println("You're alive by a miracle");
            }
        }

    }

    public void printWeight() {
        System.out.println(currentWeight+"/"+maxWeight+" kg.");
    }

    public boolean modifyWeight(double weight) {
        if (this.currentWeight+weight <= maxWeight){
            this.currentWeight+=weight;
            this.currentWeight=Math.round(currentWeight*10.0)/10.0;

            if (currentWeight < 0){
                currentWeight=0;
            }
            return true;
        }

        return false;
    }

    public boolean isAlive(){
        return alive;
    }


    //returns the inventory hashmap
    public HashMap<String, Pair> getInventory() {
        return inventory;
    }

    //prints the inventory
    public void printInventory(){
        if (inventory.size()==0){
            System.out.println("You have no items.");
        } else {
            System.out.println("You have these items:");
            for (Pair pair : inventory.values()) {
                Item currentItem = (Item) pair.getEntity();
                System.out.println("- "+pair.getCount()+"x " + currentItem.getName()+". "+currentItem.getWeight()*pair.getCount()+" kg.");
            }
        }
    }

    //this function adds an item to the inventory
    //returns false if item is not in inv or weight is nope
    public boolean addItem(Item item) {
        if (modifyWeight(item.getWeight())){
            if (hasItemInInventory(item.getID())){
                Pair fromInventory = inventory.get(item.getID());
                fromInventory.addCount();
                return true;
            } else {
                inventory.put(item.getID(), new Pair(item, 1));
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean addItem(Item item, Integer amount) {
        if (modifyWeight(item.getWeight() * amount)){
            if (hasItemInInventory(item.getID())){
                Pair fromInventory = inventory.get(item.getID());
                fromInventory.modifyCount(amount);
                return true;
            } else {
                inventory.put(item.getID(), new Pair(item, amount));
                return true;
            }
        } else {
            return false;
        }
    }

    //this function checks if the player has an item in the inventory
    //if there is no item, it returns false
    private boolean hasItemInInventory(String item){
        for (Pair p : inventory.values()){
            if (p.getEntity().getID().equals(item)){
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
            System.out.println("No such item in your inventory.");
            return null;
        }
    }

    //getter for currentRoom
    public Room getCurrentRoom() {
        return currentRoom;
    }

    //setter for currentRoom
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }


    //this function checks if the direction selected is accessible from the
    //currentRoom and if it's not locked by a door, if so it moves there
    public boolean moveToRoom(String direction, HashMap<String, Room> rooms){

        if(currentRoom.isLocked(direction)){
            System.out.println("The way "+direction+" is blocked.");
            return false;
        }
        //iterate over all directions of currentRoom, eg. north
        for (String s : currentRoom.getExits().keySet()){
            //check them with the given direction
            if (s.equals(direction)){ //north == north
                System.out.println("You move "+direction);

                //get the next room's ID
                String nextRoomId = currentRoom.getExits().get(s);

                //set next room
                setCurrentRoom(rooms.get(nextRoomId));

                return true;
            }
        }
        System.out.println("You can't figure out how to go " + direction);
        return false;
    }


    //this makes the player use an item
    public boolean simpleUse(Entity target){

        if (target.getType() == 'p'){
            Prop prop = (Prop) target;
            return prop.used(currentRoom, this);

        } else if (target.getType() == 'i'){
            Item item = (Item) target;
            return item.used(currentRoom, this);
        }
        return true;
    }


    //this function makes the player use item on target, item is an inventory Item, target is a Prop
    public boolean interactOnWith(Entity target, Entity item){

//        Prop entity = null;
//        Item invItem = null;
//
//        if (currentRoom.hasEntity(target)){
//            entity = (Prop) currentRoom.getEntityPair(target).getEntity();
//        } else if( this.hasItemInInventory(target) ){
//            invItem = (Item) getItemPair(target).getEntity();
//        }
//
//        if (currentRoom.hasEntity(item)){
//            entity = (Prop) currentRoom.getEntityPair(item).getEntity();
//        } else if( this.hasItemInInventory(target) ){
//            invItem = (Item) getItemPair(target).getEntity();
//        }

//        if (entity!= null && invItem!=null){
//            return entity.usedWith(invItem, currentRoom);
//        } else {
//            return false;
//        }



        Prop prop = (Prop) target;
        return prop.usedWith((Item) item, currentRoom);



//        Prop prop = getProp(target);
//        Pair inventoryItem = getItemPair(item);
//
//        if (prop != null && inventoryItem != null){
//            prop.usedWith(inventoryItem.getItem(), currentRoom);
////            System.out.println("You use the " + item + " on the "+ target);
//
//            if(prop instanceof LockedDoor){
//                //// TODO: 08/03/2017 maybe fix this, somehow
//                currentRoom.unlockDirection(prop.getID());
//            }
//
//            return true;
//        } else {
////            System.out.println("You can't see it.");
//            return false;
//        }
    }

    //this function prints the description of target, be it a prop or an Item
    public boolean examine(Entity target){

        if(target != null){
            target.printDescription();
            return true;
        }
        return false;

    }

    //this function makes the player drop target, if it has it
    public void drop(Pair target, boolean all){
        if (target != null){
            Item item = (Item) target.getEntity();

            //case we drop 1 & target is in room
            Pair roomItem = currentRoom.getEntityPair(target.getEntity().getID());
            if (!all && roomItem != null){
                roomItem.addCount();
                //case we drop 1 & target is not in room
            } else if (!all){
                currentRoom.addItem((Item) target.getEntity());
                modifyWeight(-((Item) target.getEntity()).getWeight());
            }

            //case we drop all & target is in room
            if (all && roomItem != null){
                roomItem.setCount(roomItem.getCount()+target.getCount());
            } else if (all){
                currentRoom.addItem((Item) target.getEntity(), target.getCount());
            }

            //we dropped 1
            if(!all){
                modifyWeight(-((Item) target.getEntity()).getWeight());
                if (target.subCount()){
                    inventory.remove(target.getEntity().getID());
                }
                //we dropped all
            } else{
                modifyWeight(-(((Item) target.getEntity()).getWeight()*target.getCount()));
                inventory.remove(target.getEntity().getID());
            }

            System.out.println("You drop the "+target.getEntity().getName());

        } else {
            System.out.println("You don't seem to have a "+target+" with you.");
        }
    }


    //this function tries to pick up an item in the currentRoom:
    //it also checks if the player can carry the current item
    //if it didn't succeed, print an error message
    public boolean pickUpItem(Pair item, boolean all){

        Item toAdd = (Item) item.getEntity();
        if (all){
            if (!addItem(toAdd, item.getCount())){
                System.out.println("Too heavy to carry.");
            }
        } else {
            if (!addItem(toAdd)){
                System.out.println("Too heavy to carry.");
            }
        }
        return true;

//        Pair fromInventory = getItemPair(item.getID());
//        //if the item exists in the room, add it to inventory
//        for (Pair pair : currentRoom.getEntities().values()){
//            Item object = (Item) pair.getEntity();
//            if (object.getID().toLowerCase().equals(item.getID().toLowerCase())){
//
//                //case we want just 1 item & item is already in inventory
//                if ( fromInventory != null){
//                    if (!all && !modifyWeight(((Item) fromInventory.getEntity()).getWeight())){
//                        System.out.println("Too heavy to carry.");
//                        return;
//                    } else if (!all){
//                        fromInventory.addCount();
//                        if (pair.subCount()){
//                            currentRoom.getEntities().remove(fromInventory.getEntity().getID());
//                        }
////                        modifyWeight(fromInventory.getItem().getWeight());
//                        System.out.println("Added "+ fromInventory.getEntity().getName() + " to inventory.");
//                        return;
//                    }
//
//
//                    //case we want to take all items & item is already in inventory
//                    if (!modifyWeight(pair.getCount()*object.getWeight())){
//
//                        return;
//                    } else {
//                        fromInventory.setCount(fromInventory.getCount()+pair.getCount());
////                        modifyWeight(pair.getItem().getWeight()*pair.getCount());
//                        currentRoom.getEntities().remove(object.getID());
//                        System.out.println("Added "+pair.getCount()+" x "+ fromInventory.getEntity().getName() + " to inventory.");
//                        return;
//                    }
//
//                }
//
//
//                //case we want to take 1 item & item is not already in inventory
//                if (!all && !modifyWeight(object.getWeight())){
//                    System.out.println("Too heavy to carry.");
//                    return;
//                } else if (!all){
//                    addItem(object);
////                    modifyWeight(object.getWeight());
//                    System.out.println("Added "+ object.getName() + " to inventory.");
//                    if (pair.subCount()){
//                        currentRoom.getEntities().remove(item);
//                    }
//                    return;
//                }
//
//                //case we want to take all items & item is not in inventory
//                if (!modifyWeight(object.getWeight()*pair.getCount())){
//                    System.out.println("Too heavy to carry.");
//                    return;
//                } else {
//                    addItem(object, pair.getCount());
////                    modifyWeight(object.getWeight()*pair.getCount());
//                    System.out.println("Added "+pair.getCount()+" x "+ object.getName() + " to inventory.");
//                    currentRoom.getEntities().remove(object.getID());
//                    return;
//                }
//            }
//        }

//        System.out.println("There is no such object");
    }


    public boolean customAction(String action, Entity item){

        if(item.getType()=='i'){
            Item inventoryItem = (Item) item;
            for (String command : item.getRequiredCommand()){
                if (command.equals(action)){
                    item.used(currentRoom, this);
                    return true;
                }
            }

        } else if (item.getType() == 'p'){
            Prop prop = (Prop) item;
            for (String command : prop.getRequiredCommand()){
                if (command.equals(action)){
                    prop.used(currentRoom, this);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean talkToNPC(String name, String subject){
        NPC npc = currentRoom.getNPC(name);
        if (npc != null && npc.talkAbout(subject)){
            return true;
        }
        return false;
    }

    public boolean attack(String name){
        Entity entity = currentRoom.getEntityPair(name).getEntity();
        if(entity.getType() == 'n'){
            System.out.println("It's best not to attack "+name);
            return false;
        } else if (entity.getType() == 'e'){
            Enemy enemy = (Enemy) entity;
            String type;
            if(wieldedWeapon==null){
                type="fists";
            } else{
                type=wieldedWeapon.getDamageType();
            }
            if (enemy.isWeakAgainst(type) && type.equals("fists")) {
                enemy.modifyHealth(-1);
            }else if(enemy.isWeakAgainst(type) ){
                enemy.modifyHealth(-wieldedWeapon.getDamage());
            } else{
                System.out.println("The "+name+" seem not to be affected");
            }
            if (enemy.isAttacked(this, currentRoom)) {
                currentRoom.getEnemyList().remove(enemy.getID());
            }
            return true;
        } else {
            System.out.println("You can't seem to see a fiend.");
        }
        return false;

    }


    public boolean hasEnoughMoney(int amt){
        Pair pair = inventory.get("coin");
        if(pair != null){
            int amount = pair.getCount();
            return amount - amt >= 0;
        } else {
            return false;
        }
    }

    public boolean give(NPC npc, Entity item){
        if(hasItemInInventory(item.getID())){
            Pair pair = getItemPair(item.getID());

            if (npc.isGiven((Item) pair.getEntity(), this)){
                return true;
            } else{
                return false;
            }
        } else{
            System.out.println("You don't have it.");
            return true;
        }
    }



}