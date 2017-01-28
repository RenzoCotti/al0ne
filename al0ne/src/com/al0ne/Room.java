package com.al0ne;

import com.al0ne.Interactables.Items.Archetypes.Interactable;
import com.al0ne.Interactables.Items.Archetypes.Pickable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Room is:
 * an interactables, all obj that one can interact with
 * a description
 * a name
 * an exits, arraylist of strings, ids in hashmap
 *
 */
public class Room {

    private HashMap<String, Interactable> interactables;
    private HashMap<String, Pickable> items;
    private String description;
    private String name;
    private ArrayList<String> exits;

    public Room(String description, String name) {
        this.description = description;
        this.name = name;
        this.interactables=new HashMap<>();
        this.items=new HashMap<>();
        this.exits=new ArrayList<>();
    }

    public HashMap<String, Interactable> getInteractables() {
        return interactables;
    }

    public void printInteractables(){
        if (items.size()==0){
            return;
        } else {
            for (Interactable item : interactables.values()) {
                System.out.println(item.getDescription());
            }
        }
    }

    public HashMap<String, Pickable> getItems() {
        return items;
    }

    public void printItems(){
        if (items.size()==0){
            return;
        } else {
            System.out.println("You can see:");
            for (Pickable item : items.values()) {
                System.out.println("- " + item.getName());
            }
        }
    }


    public String getDescription() {
        return description;
    }

    public void printDescription(){
        System.out.println(description);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getExits() {
        return exits;
    }

    public void addInteractable(Interactable item) {
        this.interactables.put(item.getName(), item);
    }

    public void addItem(Pickable item) {
        this.items.put(item.getName(), item);
    }

    public void addExit(String exit) {
        this.exits.add(exit);
    }
}
