package com.al0ne.Items;

import com.al0ne.Entities.Player;
import com.al0ne.Room;

import java.util.ArrayList;

/**
 * A Prop has:
 * - id: used to reference to that prop code-wise
 * - name: actual name of the item
 * - description: description of the prop, displayed when examining
 * - after: description of the prop *after* activation
 * - requiresItem: ItemID required for activation, e.g. cave1key, can be default
 * - active: true if the item has been activated
 * - requiredType: ArrayList of types of Item required for activation; e.g. for a rope, sharp Items are required
 * - requiredCommand: custom actions that can be applied to the item
 */
public class Prop {

    protected String id;
    protected String name;
    protected String description;
    private String after;
    protected String requiresItem;
    protected boolean active;
    private ArrayList<String> requiredType;
    private ArrayList<String> requiredCommand;


    public Prop(String id, String name, String description) {
        this.id = id;
        this.requiredType = new ArrayList<>();
        this.name = name;
        this.description = description;
        this.after = description;
        this.requiresItem="none";
        this.active=false;
        this.requiredCommand=new ArrayList<>();
    }

    public Prop(String id, String name, String description, String after) {
        this.id=id;
        this.requiredType = new ArrayList<>();
        this.name = name;
        this.description = description;
        this.after = after;
        this.requiresItem="none";
        this.active=false;
        this.requiredCommand=new ArrayList<>();
    }

    protected void addType(String type){
        requiredType.add(type);
    }

    public void addCommand(String cmd){
        requiredCommand.add(cmd);
    }

    public boolean usedWith(Item item, Room currentRoom) {
        for (String s: requiredType){
            if (item.hasProperty(s)){
                active=true;
                return true;
            }
        }
        return false;
    }

    public boolean used(){
        if (requiresItem.equals("none")){
            active=true;
            return true;
        } else{
            return false;
        }
    }


    public boolean used(Room currentRoom, Player player){
        if (requiresItem.equals("none")){
            active=true;
            return true;
        } else{
            return false;
        }
    }

    public void printDescription(){
        if(!active){
            System.out.println(description);
        } else {
            System.out.println(after);
        }
    }

    public String getDescription() {
        if(!active){
            return description;
        } else {
            return after;
        }
    }

    public String getName() {
        return name;
    }

    public String getID(){
        return id;
    }

    public ArrayList<String> getRequiredCommand() {
        return requiredCommand;
    }
}
