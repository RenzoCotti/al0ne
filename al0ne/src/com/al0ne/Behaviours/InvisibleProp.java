package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Material;

import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 08/05/2017.
 */
public class InvisibleProp extends Prop{

    protected boolean addsItem=false;
    protected ArrayList<Interactable> items;
    protected String onToggle;

    public InvisibleProp(String name, String description, String shortDescription, Material m) {
        super(name, description, shortDescription, m);
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

    @Override
    public int used(Room currentRoom, Player player) {
        if(!addsItem){
            return super.used(currentRoom, player);
        } else{
            printToLog(onToggle);
            for(Interactable i : items){
                currentRoom.addEntity(i);
            }
            return 2;
        }
    }
}
