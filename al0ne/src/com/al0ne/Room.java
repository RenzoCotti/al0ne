package com.al0ne;

import com.al0ne.Interactables.Items.Archetypes.Interactable;
import com.al0ne.Interactables.Items.Archetypes.Pickable;

import java.util.ArrayList;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Room is:
 * an interactable, all obj that one can interact with
 * a description
 * a name
 * an exits, arraylist of strings, ids in hashmap
 *
 */
public class Room {

    private ArrayList<Interactable> interactable;
    private ArrayList<Pickable> items;
    private String description;
    private String name;
    private ArrayList<String> exits;

    public Room(String description, String name) {
        this.description = description;
        this.name = name;
        this.interactable=new ArrayList<>();
        this.items=new ArrayList<>();
        this.exits=new ArrayList<>();
    }

    public ArrayList<Interactable> getInteractable() {
        return interactable;
    }

//    public void printInteractables(){
//        System.out.println("You can see:");
//        for (Interactable item : items){
//            System.out.println("- "+item.);
//        }
//    }

    public ArrayList<Pickable> getItems() {
        return items;
    }

    public void printItems(){
        System.out.println("You can see:");
        for (Pickable item : items){
            System.out.println("- "+item.getName());
        }
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

    public void addInteractable(Interactable item) {
        this.interactable.add(item);
    }

    public void addItem(Pickable item) {
        this.items.add(item);
    }

    public void addExit(String exit) {
        this.exits.add(exit);
    }
}
