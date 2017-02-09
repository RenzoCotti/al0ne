package com.al0ne;

import com.al0ne.Items.Item;
import com.al0ne.Items.LockedDoor;
import com.al0ne.Items.Prop;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Player is:
 * an inventory
 * a currentRoom
 * a maxWeight
 * a currentWeight
 *
 */
public class Player {

    private HashMap<String, Item> inventory;
    private Room currentRoom;
    private double maxWeight;
    private double currentWeight;

    public Player(Room currentRoom, double maxWeight) {
        this.currentRoom = currentRoom;
        this.inventory = new HashMap<>();
        this.maxWeight=maxWeight;
        this.currentWeight=0;
    }


    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    public void printInventory(){
        if (inventory.size()==0){
            System.out.println("You have no items.");
            return;
        } else {
            System.out.println("You have these items:");
            for (Item item : inventory.values()) {
                System.out.println("- " + item.getName());
            }
        }
    }

    public void addItem(Item item) {
        this.inventory.put(item.getID(), item);
    }

    public boolean hasItem(String item){
        try{
            inventory.get(item);
            return true;
        } catch (NullPointerException ex){
            return false;
        }
    }

    public Item getItemFromInventory(String item){
        if(hasItem(item)){
            return inventory.get(item);
        } else {
            System.out.println("No such item in your inventory.");
            return null;
        }
    }

    public void removeFromInventory(String item){
        if (hasItem(item)){
            inventory.remove(item);
        } else{
            System.out.println("Error: tried removing non-existing object");
        }
    }



    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void pickUpItem(String item){

        for (Item object : currentRoom.getItems().values()){
//            System.out.println(object.getName());
            if (object.getName().toLowerCase().equals(item.toLowerCase())){
                if (object.getWeight()+currentWeight > maxWeight ){
                    System.out.println("Too heavy to carry.");
                    return;
                } else{
                    addItem(object);
                    currentWeight+=object.getWeight();
                    System.out.println("Added "+ object.getName() + " to inventory.");
                    currentRoom.getItems().remove(item);
                    return;
                }
            }
        }

        System.out.println("There is no such object");
    }

    //Room.exits: <north, cave1>
    //rooms: <cave1, Room>

    public void moveToRoom(String direction, HashMap<String, Room> rooms){

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

    public void interactOnWith(String target, String item){

        Prop prop = getProp(target);
        Item inventoryItem = getItemFromInventory(item);

        if (prop != null && inventoryItem != null){
            System.out.println("You use the " + inventoryItem.getName() + " on the "+ prop.getName());
            prop.usedWith(inventoryItem);
            if(prop instanceof LockedDoor){
                currentRoom.unlockDirection(prop.getID());
            }
        } else {
            System.out.println("You can't see it.");
        }
    }

    public void examine(String target){
        Prop prop = getProp(target);
        Item item = getItemFromInventory(target);
        if (prop != null){
            prop.printDescription();
        } else if(item != null){
            item.printDescription();
        } else {
            System.out.println("You can't see a "+target);
        }
    }

    public Prop getProp(String target){
        Prop prop = currentRoom.getProps().get(target);

        if( prop != null) {
            return prop;
        } else{
//            System.out.println("You can't see that item.");
            return null;
        }
    }
}


//
//    public boolean use(String target){
//
//        Interactable interacted = getItem(target);
//        Pickable inventoryItem = getItemFromInventory(target);
//
//        if (interacted != null && inventoryItem != null && inventoryItem != interacted){
//            System.out.println("Which one, the one in your inventory or the one in the room? (R/I)");
//            Scanner asd = new Scanner(System.in);
//            while (true){
//                if (asd.hasNextLine()){
//                    String input = asd.nextLine().toLowerCase();
//                    if (input.equals("R")){
//                        interacted.toggle();
//                        return true;
//                    } else if(input.equals("I")){
//                        inventoryItem.toggle();
//                        return true;
//                    }
//                }
//            }
//        } else if (inventoryItem != null){
//            System.out.println("You use the " + target);
//            inventoryItem.toggle();
//            return true;
//        } else if (interacted != null){
//            System.out.println("You use the "+target);
//            interacted.toggle();
//            return true;
//        } else {
//            System.out.println("You can't seem to find the "+target);
//            return false;
//        }
//    }
//