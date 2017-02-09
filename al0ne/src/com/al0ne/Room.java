package com.al0ne;

import com.al0ne.Items.Item;
import com.al0ne.Items.Prop;

import java.util.ArrayList;
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

    private HashMap<String, Prop> props;
    private HashMap<String, Item> items;
    private String description;
    private String name;
    private String id;
    private HashMap<String, String> exits;
    private HashMap<String, String> lockedDirections;

    public Room(String id, String name, String description) {
        this.id=id;
        this.description = description;
        this.name = name;
        this.props=new HashMap<>();
        this.items=new HashMap<>();
        this.exits=new HashMap<>();
        this.lockedDirections =new HashMap<>();
    }

    public HashMap<String, Prop> getProps() {
        return props;
    }

    public void printProps(){
        if (props.size()!=0){
            for (Prop prop : props.values()) {
                prop.printDescription();
            }
        }
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void printItems(){
        if (items.size()!=0){
            System.out.println("You can see:");
            for (Item item : items.values()) {
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

    public String getId(){
        return id;
    }

    public void printName() {
        System.out.println(name);
    }

    public void addProp(Prop prop) {
        this.props.put(prop.getID(), prop);
    }

    public void addItem(Item item) {
        this.items.put(item.getID(), item);
    }

    public void addExit(String exit, String roomid) {
        this.exits.put(exit, roomid);
    }

    public void printRoom(){
        System.out.println(name);
        printDescription();
        printProps();
        printItems();
        System.out.println();
    }

    public void lockDirection(String direction, String idDoor){
        lockedDirections.put(idDoor, direction);
    }

    public void unlockDirection(String nameDoor){
        lockedDirections.remove(nameDoor);
    }

    public boolean isLocked(String direction){
        for (String s : lockedDirections.values()){
            if (s.equals(direction)){
                return true;
            }
        }
        return false;
    }

}
