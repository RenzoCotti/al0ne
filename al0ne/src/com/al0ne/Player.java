package com.al0ne;

import com.al0ne.Items.Behaviours.Food;
import com.al0ne.Items.Behaviours.Weapon;
import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
import com.al0ne.Items.Props.LockedDoor;
import com.al0ne.Items.Prop;

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
 * TODO probably add health, belly
 */
public class Player {

    //Maps ItemID, Item
    private HashMap<String, Pair> inventory;
    private Room currentRoom;
    
    //Maximum carry weight of the player
    private static double maxWeight=10;
    //Current carry weight of the player
    private double currentWeight;

    private int health=10;
    private static int maxHealth=10;
    private boolean alive = true;

    private Weapon wieldedWeapon;
    
    // TODO: 08/03/2017 add health, satiation

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
            Item currentItem = pair.getItem();

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

    public int getHealth() {
        return health;
    }

    public void printHealth() {
        System.out.println("You have "+health+"/"+maxHealth+" HP.");
    }

    public void modifyHealth(int health) {
        if (this.health+health <= maxHealth){
            this.health+=health;
        }
        if (this.health <= 0){
            alive=false;
        }
    }

    public void printWeight() {
        System.out.println(currentWeight+"/"+maxWeight+" kg.");
    }

    private boolean modifyWeight(double weight) {
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
                System.out.println("- "+pair.getCount()+"x " + pair.getItem().getName()+". "+pair.getItem().getWeight()*pair.getCount()+" kg.");
            }
        }
    }


    //this function returns a prop, if it exists
    private Prop getProp(String target){
        Prop prop = currentRoom.getProps().get(target);

        if( prop != null) {
            return prop;
        } else{
            return null;
        }
    }

    //this function adds an item to the inventory
    public void addItem(Item item) {
        this.inventory.put(item.getID(), new Pair(item, 1));
    }

    private void addItem(Item item, Integer amount) {
        this.inventory.put(item.getID(), new Pair(item, amount));
    }

    //this function checks if the player has an item in the inventory
    //if there is no item, it returns false
    private boolean hasItem(String item){
        try{
            inventory.get(item);
            return true;
        } catch (NullPointerException ex){
            return false;
        }
    }

    //this function tries to get an item from the inventory
    //if there is no such item, it returns null
    public Pair getPairFromInventory(String item){
        if(hasItem(item)){
            return inventory.get(item);
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
//        System.out.println();
        return false;
    }


    //this makes the player use an item
    public void simpleUse(String target){
        Prop prop = null;
        Item item = null;
        try{
            prop = getProp(target);

        }catch (NullPointerException ex){
            item = getPairFromInventory(target).getItem();
        }

        if (prop == null && item != null){
            //case its an item in inventory
            if(item.hasProperty("consumable")){
                //// TODO: 11/02/2017
            } else if( item.hasProperty("food")){
                Food food = (Food) item;
                food.used();
                inventory.remove(item.getID());
            } else {
                System.out.println("You can't figure out how to use it.");
            }

        } else if (prop != null){
            //case its a prop
            prop.used();
            prop.used(currentRoom);
        } else {
            System.out.println("You can't seem to see a "+target);

        }

    }


    //this function makes the player use item on target, item is an inventory Item, target is a Prop
    public boolean interactOnWith(String target, String item){

        Prop prop = getProp(target);
        Pair inventoryItem = getPairFromInventory(item);

        if (prop != null && inventoryItem != null){
            prop.usedWith(inventoryItem.getItem(), currentRoom);
//            System.out.println("You use the " + item + " on the "+ target);

            if(prop instanceof LockedDoor){
                //// TODO: 08/03/2017 maybe fix this, somehow
                currentRoom.unlockDirection(prop.getID());
            }

            return true;
        } else {
//            System.out.println("You can't see it.");
            return false;
        }
    }

    //this function prints the description of target, be it a prop or an Item
    public void examine(String target){
        Prop prop = getProp(target);

        Item groundItem=null;
        if (currentRoom.hasItem(target)){
            groundItem=currentRoom.getItems().get(target).getItem();
        }

        Pair pair=getPairFromInventory(target);
        Item item=null;

        if(pair != null){
            item = pair.getItem();
        }

        if (prop != null){
            prop.printDescription();
        } else if(item != null){
            item.printDescription();
        } else if(groundItem != null){
            groundItem.printDescription();
        } else {
            System.out.println("You can't see a "+target);
        }
    }

    //this function makes the player drop target, if it has it
    public void drop(String target, boolean all){
        Pair item = getPairFromInventory(target);
        if (item != null){

            //case we drop 1 & item is in room
            Pair roomItem = currentRoom.getPair(item.getItem().getID());
            if (!all && roomItem != null){
                roomItem.addCount();
                //case we drop 1 & item is not in room
            } else if (!all){
                currentRoom.addItem(item.getItem());
                modifyWeight(-item.getItem().getWeight());
            }

            //case we drop all & item is in room
            if (all && roomItem != null){
                roomItem.setCount(roomItem.getCount()+item.getCount());
            } else if (all){
                currentRoom.addItem(item.getItem(), item.getCount());
            }

            //we dropped 1
            if(!all){
                modifyWeight(-item.getItem().getWeight());
                if (item.subCount()){
                    inventory.remove(item.getItem().getID());
                }
                //we dropped all
            } else{
                modifyWeight(-((item.getItem().getWeight()*item.getCount())));
                inventory.remove(item.getItem().getID());
            }

            System.out.println("You drop the "+item.getItem().getName());

        } else {
            System.out.println("You don't seem to have a "+target+" with you.");
        }
    }


    //this function tries to pick up an item in the currentRoom:
    //it also checks if the player can carry the current item
    //if it didn't succeed, print an error message
    public void pickUpItem(String item, boolean all){

        Pair fromInventory = getPairFromInventory(item);
        //if the item exists in the room, add it to inventory
        for (Pair pair : currentRoom.getItems().values()){
            Item object = pair.getItem();
            if (object.getID().toLowerCase().equals(item.toLowerCase())){

                //case we want just 1 item & item is already in inventory
                if ( fromInventory != null){
                    if (!all && !modifyWeight(fromInventory.getItem().getWeight())){
                        System.out.println("Too heavy to carry.");
                        return;
                    } else if (!all){
                        fromInventory.addCount();
                        if (pair.subCount()){
                            currentRoom.getItems().remove(fromInventory.getItem().getID());
                        }
//                        modifyWeight(fromInventory.getItem().getWeight());
                        System.out.println("Added "+ fromInventory.getItem().getName() + " to inventory.");
                        return;
                    }


                    //case we want to take all items & item is already in inventory
                    if (!modifyWeight(pair.getCount()*object.getWeight())){
                        System.out.println("Too heavy to carry.");
                        return;
                    } else {
                        fromInventory.setCount(fromInventory.getCount()+pair.getCount());
//                        modifyWeight(pair.getItem().getWeight()*pair.getCount());
                        currentRoom.getItems().remove(object.getID());
                        System.out.println("Added "+pair.getCount()+" x "+ fromInventory.getItem().getName() + " to inventory.");
                        return;
                    }

                }


                //case we want to take 1 item & item is not already in inventory
                if (!all && !modifyWeight(object.getWeight())){
                    System.out.println("Too heavy to carry.");
                    return;
                } else if (!all){
                    addItem(object);
//                    modifyWeight(object.getWeight());
                    System.out.println("Added "+ object.getName() + " to inventory.");
                    if (pair.subCount()){
                        currentRoom.getItems().remove(item);
                    }
                    return;
                }

                //case we want to take all items & item is not in inventory
                if (!modifyWeight(object.getWeight()*pair.getCount())){
                    System.out.println("Too heavy to carry.");
                    return;
                } else {
                    addItem(object, pair.getCount());
//                    modifyWeight(object.getWeight()*pair.getCount());
                    System.out.println("Added "+pair.getCount()+" x "+ object.getName() + " to inventory.");
                    currentRoom.getItems().remove(object.getID());
                    return;
                }
            }
        }

//        System.out.println("There is no such object");
    }


    public boolean customAction(String action, String item){
        Prop prop;
        Item inventoryItem;
        try{
            prop = getProp(item);
            for (String command : prop.getRequiredCommand()){
//            System.out.println(command);
                if (command.equals(action)){
                    prop.used(currentRoom);
                    return true;
                }
            }
            return false;
        } catch(NullPointerException ex){
            inventoryItem = getPairFromInventory(item).getItem();
            for (String command : inventoryItem.getRequiredCommand()){
//            System.out.println(command);
                if (command.equals(action)){
                    inventoryItem.used(currentRoom, this);
                    return true;
                }
            }
            return false;
        }


    }

    public boolean talkToNPC(String name, String subject){
        NPC npc = currentRoom.getNPC(name);
        if (npc != null && npc.talkAbout(subject)){
            return true;
        }
        return false;
    }

    public boolean attack(String name){
        printHealth();
        Enemy enemy = currentRoom.getEnemy(name);
        enemy.printHealth();
        String type;
        if(wieldedWeapon==null){
            type="fists";
        } else{
            type=wieldedWeapon.getType();
        }
        if (enemy != null){
            if (enemy.isWeakAgainst(type)) {
                enemy.modifyHealth(-wieldedWeapon.getDamage());
            } else{
                System.out.println("The "+name+" seem not to be affected");
            }
            if (enemy.isAttacked(this, currentRoom)) {
//                enemy.addLoot(currentRoom);
                currentRoom.getEnemyList().remove(enemy.getID());
            }
            printHealth();
            enemy.printHealth();
            return true;
        } else {
            System.out.println("You can't seem to see a fiend.");
        }
        return false;
    }



}