package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Entity;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Entities.Items.Behaviours.ChargeItem;
import com.al0ne.Entities.Items.Behaviours.Container;

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

    protected String afterDescription;
    protected String requiresItem;
    protected boolean active;
    protected ArrayList<String> requiredType;
    protected ArrayList<String> properties;


    public Prop(String id, String name, String description, String shortDescription) {
        super(id, name, description, shortDescription);
        this.requiredType = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.afterDescription = description;
        this.requiresItem="none";
        this.active=false;
        this.type='p';
    }

    public Prop(String id, String name, String description, String shortDescription, String after) {
        super(id, name, description, shortDescription);
        this.requiredType = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.afterDescription = after;
        this.requiresItem="none";
        this.active=false;
        this.type='p';
    }


    protected void addType(String type){
        requiredType.add(type);
    }

    public void addProperty(String property){
        properties.add(property);
    }

    public boolean hasProperty(String property){
        return properties.contains(property);
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

        if(item instanceof ChargeItem){
            ChargeItem charge = (ChargeItem) item;
            return charge.refill(this);
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
    public void printLongDescription(Player player, Room room){
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
