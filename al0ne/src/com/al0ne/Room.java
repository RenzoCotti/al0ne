package com.al0ne;

import com.al0ne.Interactables.Items.Archetypes.Interactable;
import com.al0ne.Interactables.Items.Archetypes.Pickable;
import java.util.HashMap;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Room is:
 * an interactables, all obj that one can interact with
 * an items, all objects you can pickup
 * a description
 * a name
 * an exits, hashmap of direction - roomid
 *
 */
public class Room {

    private HashMap<String, Interactable> interactables;
    private HashMap<String, Pickable> items;
    private String description;
    private String name;
    private HashMap<String, String> exits;

    public Room(String description, String name) {
        this.description = description;
        this.name = name;
        this.interactables=new HashMap<>();
        this.items=new HashMap<>();
        this.exits=new HashMap<>();
    }

    public HashMap<String, Interactable> getInteractables() {
        return interactables;
    }

    public void printInteractables(){
        if (items.size()!=0){
            for (Interactable item : interactables.values()) {
                System.out.println(item.getDescription());
            }
        }
    }

    public HashMap<String, Pickable> getItems() {
        return items;
    }

    public void printItems(){
        if (items.size()!=0){
            System.out.println("You can see:");
            for (Pickable item : items.values()) {
                System.out.println("- " + item.getName());
            }
        }
    }

    public HashMap<String, String> getExits() {
        return exits;
    }

    public String getDescription() {
        return description;
    }

    private void printDescription(){
        System.out.println(description);
    }

    public String getName() {
        return name;
    }

    public void printName() {
        System.out.println(name);
    }

    public void addInteractable(Interactable item) {
        this.interactables.put(item.getName(), item);
    }

    public void addItem(Pickable item) {
        this.items.put(item.getName(), item);
    }

    public void addExit(String exit, String roomid) {
        this.exits.put(exit, roomid);
    }

    public void printRoom(){
        System.out.println(name);
        printDescription();
//        printInteractables();
        printItems();
        System.out.println();
    }
}
