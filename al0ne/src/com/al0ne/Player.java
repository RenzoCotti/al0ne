package com.al0ne;

import com.al0ne.Items.Behaviours.Food;
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
    private double maxWeight=60;
    //Current carry weight of the player
    private double currentWeight;
    
    // TODO: 08/03/2017 add health, satiation

    //creates a new Player, sets the current Room to currentRoom
    //inventory is empty, weight is 0
    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.inventory = new HashMap<>();
        this.currentWeight=0;
    }


    //returns the inventory hashmap
    public HashMap<String, Pair> getInventory() {
        return inventory;
    }

    //prints the inventory
    public void printInventory(){
        if (inventory.size()==0){
            System.out.println("You have no items.");
            return;
        } else {
            System.out.println("You have these items:");
            for (Pair pair : inventory.values()) {
                System.out.println("- "+pair.getCount()+"x " + pair.getItem().getName());
            }
        }
    }


    //this function returns a prop, if it exists
    public Prop getProp(String target){
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

    public void addItem(Item item, Integer amount) {
        this.inventory.put(item.getID(), new Pair(item, amount));
    }

    //this function checks if the player has an item in the inventory
    //if there is no item, it returns false
    public boolean hasItem(String item){
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
    public void moveToRoom(String direction, HashMap<String, Room> rooms){

        if(currentRoom.isLocked(direction)){
            System.out.println("The way "+direction+" is blocked.");
            return;
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

                System.out.println();
                currentRoom.printRoom();
                return;
            }
        }
        System.out.println("You can't figure out how to go " + direction);
        System.out.println();
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

        } else if (prop != null && item == null){
            //case its a prop
            prop.used();
            prop.used(currentRoom);
        } else {
            System.out.println("You can't seem to see a "+target);

        }

    }


    //this function makes the player use item on target, item is an inventory Item, target is a Prop
    public void interactOnWith(String target, String item){

        Prop prop = getProp(target);
        Pair inventoryItem = getPairFromInventory(item);

        if (prop != null && inventoryItem != null){
            prop.usedWith(inventoryItem.getItem(), currentRoom);

            if(prop instanceof LockedDoor){
                //// TODO: 08/03/2017 maybe fix this, somehow
                currentRoom.unlockDirection(prop.getID());
            }
        } else {
            System.out.println("You can't see it.");
        }
    }

    //this function prints the description of target, be it a prop or an Item
    public void examine(String target){
        Prop prop = getProp(target);
        Item item = getPairFromInventory(target).getItem();
        if (prop != null){
            prop.printDescription();
        } else if(item != null){
            item.printDescription();
        } else {
            System.out.println("You can't see a "+target);
        }
    }

    //this function makes the player drop target, if it has it
    public void drop(String target, boolean all){
        Pair item = getPairFromInventory(target);
        if (item != null){

            //case we drop 1
            Pair roomItem = currentRoom.getPair(item.getItem().getID());
            if (!all && roomItem != null){
                roomItem.addCount();
            } else if (!all){
                currentRoom.addItem(item.getItem());
            }

            //case we drop all
            if (all && roomItem != null){
                roomItem.setCount(roomItem.getCount()+item.getCount());
            } else if (all){
                currentRoom.addItem(item.getItem(), item.getCount());
            }

            if(!all){
                item.subCount();
                inventory.remove(item.getItem().getID());
            } else{
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

                //case we want just 1 item
                if ( fromInventory != null){
                    if (!all && fromInventory.getItem().getWeight()+currentWeight > maxWeight ){
                        System.out.println("Too heavy to carry.");
                        return;
                    } else if (!all){
                        fromInventory.addCount();
                        if (pair.subCount()){
                            currentRoom.getItems().remove(fromInventory.getItem().getID());
                        }
                        currentWeight+=fromInventory.getItem().getWeight();
                        System.out.println("Added "+ fromInventory.getItem().getName() + " to inventory.");
                        return;
                    }


                    //case we want to take all items
                    if (all && (fromInventory.getItem().getWeight()*fromInventory.getCount())+currentWeight > maxWeight ){
                        System.out.println("Too heavy to carry.");
                        return;
                    } else if (all){
                        fromInventory.setCount(fromInventory.getCount()+pair.getCount());
                        currentWeight+=(fromInventory.getItem().getWeight()*fromInventory.getCount());
                        currentRoom.getItems().remove(object.getID());
                        System.out.println("Added "+fromInventory.getCount()+" x "+ fromInventory.getItem().getName() + " to inventory.");
                        return;
                    }

                }



                if (!all && object.getWeight()+currentWeight > maxWeight ){
                    System.out.println("Too heavy to carry.");
                    return;
                } else if (!all){
                    addItem(object);
                    currentWeight+=object.getWeight();
                    System.out.println("Added "+ object.getName() + " to inventory.");
                    if (pair.subCount()){
                        currentRoom.getItems().remove(item);
                    }
                    return;
                }

                if (all && (object.getWeight()*pair.getCount())+currentWeight > maxWeight ){
                    System.out.println("Too heavy to carry.");
                    return;
                } else if (all){
                    addItem(object, pair.getCount());
                    currentWeight+=(object.getWeight()*pair.getCount());
                    System.out.println("Added "+pair.getCount()+" x "+ object.getName() + " to inventory.");
                    currentRoom.getItems().remove(object.getID());
                    return;
                }
            }
        }

        System.out.println("There is no such object");
    }


    public boolean customAction(String action, String item){
        Prop prop = getProp(item);
        for (String command : prop.getRequiredCommand()){
//            System.out.println(command);
            if (command.equals(action)){
                prop.used(currentRoom);
                return true;
            }
        }
        return false;
    }


}