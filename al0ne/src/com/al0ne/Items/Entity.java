package com.al0ne.Items;

import com.al0ne.Entities.Player;
import com.al0ne.Room;

import java.util.ArrayList;

/**
 * Created by BMW on 02/02/2017.
 *
 * Item interface
 */
public abstract class Entity {

    protected String name;
    protected String ID;
    protected String description;


    /**
    * i: item
    * p: prop
    * n: npc
    * e: enemy
    * */
    protected char type;

    protected ArrayList<String> requiredCommand;


    public Entity(String id, String name, String description) {
        this.ID = id;
        this.name = name;
        this.description = description;
        this.requiredCommand=new ArrayList<>();
        addCommand("examine");
    }





    public char getType() {
        return type;
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


    public ArrayList<String> getRequiredCommand() {
        return requiredCommand;
    }

    protected void addCommand(String cmd){
        requiredCommand.add(cmd);
    }

}
