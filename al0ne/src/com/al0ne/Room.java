package com.al0ne;

import com.al0ne.Interactables.Items.Archetypes.Interactable;

import java.util.ArrayList;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Room is:
 * an objects, all obj that one can interact with
 * a description
 * a name
 * an exits, arraylist of strings, ids in hashmap
 *
 */
public class Room {

    private ArrayList<Interactable> objects;
    private String description;
    private String name;
    private ArrayList<String> exits;

    public Room(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public ArrayList<Interactable> getObjects() {
        return objects;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getExits() {
        return exits;
    }

    public void addItem(Interactable item) {
        this.objects.add(item);
    }

    public void addExit(String exit) {
        this.exits.add(exit);
    }
}
