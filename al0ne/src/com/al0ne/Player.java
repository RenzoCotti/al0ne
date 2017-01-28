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

    public Player(Room currentRoom, ArrayList<Pickable> inventory) {
        this.currentRoom = currentRoom;
        this.inventory = inventory;
    }


    public ArrayList<Pickable> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Pickable> inventory) {
        this.inventory = inventory;
    }



    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
