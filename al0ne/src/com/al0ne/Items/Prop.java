package com.al0ne.Items;

import com.al0ne.Entities.Entity;
import com.al0ne.Entities.Player;
import com.al0ne.Room;

import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * A Prop has:
 * - id: used to reference to that prop code-wise
 * - name: actual name of the item
 * - longDescription: longDescription of the prop, displayed when examining
 * - afterDescription: longDescription of the prop *afterDescription* activation
 * - requiresItem: ItemID required for activation, e.g. cave1key, can be default
 * - active: true if the item has been activated
 * - requiredType: ArrayList of types of Item required for activation; e.g. for a rope, sharp ConcreteItems are required
 * - requiredCommand: custom actions that can be applied to the item
 */
public class Prop extends Entity {

    private String afterDescription;
    protected String requiresItem;
    protected boolean active;
    private ArrayList<String> requiredType;


    public Prop(String id, String name, String description, String shortDescription) {
        super(id, name, description, shortDescription);
        this.requiredType = new ArrayList<>();
        this.afterDescription = description;
        this.requiresItem="none";
        this.active=false;
        this.type='p';
    }

    public Prop(String id, String name, String description, String shortDescription, String after) {
        super(id, name, description, shortDescription);
        this.requiredType = new ArrayList<>();
        this.afterDescription = after;
        this.requiresItem="none";
        this.active=false;
        this.type='p';
    }


    protected void addType(String type){
        requiredType.add(type);
    }


    public boolean usedWith(Item item, Room currentRoom, Player player) {
        for (String s: requiredType){
            if (item.hasProperty(s)){
                active=true;
                return true;
            }
        }
        if (requiresItem.equals(item.getID())){
            active=true;
            return true;
        }
        return false;
    }


    @Override
    public boolean used(Room currentRoom, Player player){
        if (requiresItem.equals("none")){
            active=true;
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void printLongDescription(){
        if(!active){
            printToLog(longDescription);
        } else {
            printToLog(afterDescription);
        }
    }

    @Override
    public String getLongDescription() {
        if(!active){
            return longDescription;
        } else {
            return afterDescription;
        }
    }

}
