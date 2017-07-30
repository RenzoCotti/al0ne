package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.abstractEntities.Interactable;

import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 08/05/2017.
 */
public class InvisibleProp extends Prop{

    protected boolean addsItem=false;
    protected ArrayList<Interactable> items;
    protected String onToggle;
    protected boolean addsOnExamine=false;

    public InvisibleProp(String name, String description, String shortDescription, Material m) {
        super(name, description, shortDescription, null, m);
        this.invisible=true;
    }

    public InvisibleProp(String name, String description) {
        super(name, description);
        this.invisible=true;
    }

    public boolean addsItem() {
        return addsItem;
    }

    public void setAddsItem(String onToggle, ArrayList<Interactable> items) {
        this.addsItem = true;
        this.onToggle = onToggle;
        this.items = items;
    }

    public void setAddsOnExamine(ArrayList<Interactable> items) {
        this.addsOnExamine = true;
        this.items = items;
    }

    @Override
    public String used(Player player) {
        Room currentRoom = player.getCurrentRoom();
        if(!addsItem){
            return super.used(player);
        } else{
            printToLog(onToggle);
            for(Interactable i : items){
                currentRoom.addEntity(i);
            }
            return "";
        }
    }

    @Override
    public void printLongDescription(Player player) {
        if(addsOnExamine){
            for(Interactable i : items){
                player.getCurrentRoom().addEntity(i);
            }
        }
        super.printLongDescription(player);

    }
}
