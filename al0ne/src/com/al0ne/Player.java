package com.al0ne;

import com.al0ne.Interactables.Items.Archetypes.Interactable;
import com.al0ne.Interactables.Items.Archetypes.Pickable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Player is:
 * an inventory
 * a currentRoom
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
                    return;
                }
            }
        }

        System.out.println("There is no such object");
    }

    public void moveToRoom(String room, HashMap<String, Room> rooms){

        for (String s : currentRoom.getExits()){
            if (s.equals(room)){
                System.out.println("You move to "+room);
                setCurrentRoom(rooms.get(room));
                System.out.println();
                return;
            }
        }
        System.out.println("You can't figure out where to go");
        System.out.println();
    }

    public void interactWith (Interactable target, Pickable item){
        target.isInteractedWith(item);
    }
}
