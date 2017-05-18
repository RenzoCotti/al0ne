package com.al0ne.Behaviours.abstractEntities;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Utility;

import java.io.Serializable;
import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 02/02/2017.
 *
 * Item interface
 */
public abstract class Entity implements Serializable{

    protected static int entityCounter = 0;


    protected String name;
    protected String ID;
    protected String longDescription;
    protected String shortDescription;


    /**
    * i: item
    * p: prop
    * n: npc
    * e: enemy
     *  w: wearable
     *  C: container
    * */
    protected char type;

    protected ArrayList<Command> requiredCommand;


    public Entity(String id, String name, String longDescription, String shortDescription) {
        this.ID = id;
        this.name = name;
        this.longDescription = longDescription;

        if(shortDescription == null){
            this.shortDescription= Utility.getArticle(name)+" "+name.toLowerCase();
        } else {
            this.shortDescription=shortDescription;
        }

        this.requiredCommand=new ArrayList<>();
        addCommand(Command.EXAMINE);
    }






    public char getType() {
        return type;
    }


    public String getName() {
        return name.toLowerCase();
    }

    public String getID(){
        return ID;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void printLongDescription(Player player, Room room) {
        printToLog(longDescription);
    }

    public ArrayList<Command> getRequiredCommand() {
        return requiredCommand;
    }

    public void addCommand(Command command){
        requiredCommand.add(command);
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
