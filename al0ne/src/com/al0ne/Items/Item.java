package com.al0ne.Items;

import com.al0ne.Player;
import com.al0ne.Room;

import java.util.ArrayList;

/**
 * Created by BMW on 02/02/2017.
 *
 * Item interface
 */
public abstract class Item {

    protected String name;
    protected String ID;
    protected String description;
    protected double weight;

    protected ArrayList<String> properties;
    protected ArrayList<String> requiredCommand;


    public Item(String id, String name, String description, double weight) {
        this.ID = id;
        this.name = name;
        this.weight = weight;
        this.description = description;
        this.properties= new ArrayList<>();
        requiredCommand=new ArrayList<>();
    }

    public void addProperty(String behaviour){
        properties.add(behaviour);
    }

    public boolean hasProperty(String property){
        for (String s : properties){
            if (s.equals(property)){
                return true;
            }
        }
        return false;
    }

    public void used(Room currentRoom, Player player){

    }


    public String getName() {
        return name;
    }

    public String getID(){
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public void printDescription() {
        System.out.println(description);
    }

    public double getWeight() {
        return weight;
    }

    public ArrayList<String> getRequiredCommand() {
        return requiredCommand;
    }

    public void addCommand(String cmd){
        requiredCommand.add(cmd);
    }


}
