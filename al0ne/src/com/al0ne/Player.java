package com.al0ne;

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

    public Pickable getItem(String item){
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

    public void interactWith (Interactable target, Pickable item){
        if (target != null && item != null){
            System.out.println("You use the " + item.getName() + " on the "+ target.getName());
            target.isInteractedWith(item);
        } else {
            System.out.println("You can't see it.");
        }
    }

    public void examine(String target){
        if (target != null){
            try{
                currentRoom.getInteractables().get(target).printDescription();
            } catch (NullPointerException ex){
                try{
                    currentRoom.getItems().get(target).printDescription();
                } catch (NullPointerException exception) {
                    try{
                        inventory.get(target).printDescription();
                    } catch (NullPointerException notFound){
                        System.out.println("You can't see that item.");
                    }
                }
            }
        }
    }
}
