package com.al0ne;

import com.al0ne.Interactables.Items.Archetypes.Pickable;

import java.util.ArrayList;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Player is:
 * an inventory
 * a currentRoom
 *
 */
public class Player {

    private ArrayList<Pickable> inventory;
    private Room currentRoom;
    private double maxWeight;
    private double currentWeight;

    public Player(Room currentRoom, ArrayList<Pickable> inventory, double maxWeight) {
        this.currentRoom = currentRoom;
        this.inventory = inventory;
        this.maxWeight=maxWeight;
        this.currentWeight=0;
    }


    public ArrayList<Pickable> getInventory() {
        return inventory;
    }

    public void addItem(Pickable item) {
        this.inventory.add(item);
    }



    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void pickUpItem(Pickable item){

        for (Pickable object : currentRoom.getItems()){
            if (object.getName().equals(item.getName())){
                if (item.getWeight()+currentWeight > maxWeight ){
                    System.out.println("Too heavy to carry.");
                    return;
                } else{
                    addItem(item);
                    System.out.println("Added "+ item.getName() + " to inventory.");
                    return;
                }
            }
        }

        System.out.println("There is no such object");
    }

    public void moveToRoom(Room room){

        for (String)

    }
}
