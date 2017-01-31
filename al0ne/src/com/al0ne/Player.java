package com.al0ne;

import com.al0ne.Interactables.Items.Archetypes.Consumable;
import com.al0ne.Interactables.Items.Archetypes.Interactable;
import com.al0ne.Interactables.Items.Archetypes.Pickable;
import java.util.HashMap;

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

    private HashMap<String, Pickable> inventory;
    private Room currentRoom;
    private double maxWeight;
    private double currentWeight;

    public Player(Room currentRoom, double maxWeight) {
        this.currentRoom = currentRoom;
        this.inventory = new HashMap<>();
        this.maxWeight=maxWeight;
        this.currentWeight=0;
    }


    public HashMap<String, Pickable> getInventory() {
        return inventory;
    }

    public void printInventory(){
        if (inventory.size()==0){
            System.out.println("You have no items.");
            return;
        } else {
            System.out.println("You have these items:");
            for (Interactable item : inventory.values()) {
                System.out.println("- " + item.getName());
            }
        }
    }

    public void addItem(Pickable item) {
        this.inventory.put(item.getName(), item);
    }

    public boolean hasItem(String item){
        try{
            inventory.get(item);
            return true;
        } catch (NullPointerException ex){
            return false;
        }
    }

    public Pickable getItemFromInventory(String item){
        if(hasItem(item)){
            return inventory.get(item);
        } else {
            System.out.println("No such item in your inventory.");
            return null;
        }
    }



    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void pickUpItem(String item){

        for (Pickable object : currentRoom.getItems().values()){
            if (object.getName().equals(item)){
                if (object.getWeight()+currentWeight > maxWeight ){
                    System.out.println("Too heavy to carry.");
                    return;
                } else{
                    addItem(object);
                    currentWeight+=object.getWeight();
                    System.out.println("Added "+ item + " to inventory.");
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

        Interactable interacted = getItem(target);
        Pickable inventoryItem = getItemFromInventory(item);

        if (interacted != null && inventoryItem != null){
            System.out.println("You use the " + inventoryItem.getName() + " on the "+ interacted.getName());
            interacted.isInteractedOnWith(inventoryItem);
        } else {
            System.out.println(interacted);
            System.out.println(inventoryItem);
            System.out.println("You can't see it.");
        }
    }

    public void use(String target){

        Interactable interacted = getItem(target);
        Pickable inventoryItem = getItemFromInventory(target);

        if (interacted != null && inventoryItem != null){
            System.out.println("Which one, the one in your inventory or the one in the room?");
        } else if (interacted != null){
            System.out.println("You use the " + target);
            interacted.toggle();
        } else if (inventoryItem != null){
            System.out.println("You use the "+target);
            inventoryItem.toggle();
        } else {
            System.out.println("You can't seem to find the "+target);
        }
    }

    public void examine(String target){
        if (getItem(target) != null){
            getItem(target).printDescription();
        }
    }

    public Interactable getItem(String target){
        Interactable interactable = currentRoom.getInteractables().get(target);
        Interactable item = currentRoom.getItems().get(target);
        Interactable inventoryItem = getItemFromInventory(target);

        if( interactable != null) {
            return interactable;
        } else if( item != null ){
            return item;
        } else if ( inventoryItem != null){
            return inventoryItem;
        } else{
            System.out.println("You can't see that item.");
            return null;
        }
    }
}
