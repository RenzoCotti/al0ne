package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;

import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 06/05/2017.
 */
public abstract class Interactable extends Entity{
    //todo: add plural?

    protected ArrayList<String> properties;
    protected ArrayList<String> requiredType;
    protected boolean canDrop;
    public boolean canTake;
    protected Material material;
    public boolean customName=false;


    public Interactable(String id, String name, String description, String shortDescription, Material m) {
        super(id, name, description, shortDescription);
        this.properties = new ArrayList<>();
        this.requiredType = new ArrayList<>();
        this.material = m;
    }

    public void setUndroppable() {
        this.canDrop = false;
    }

    public boolean canDrop(){
        return canDrop;
    }

    //returns 0 if false
    //1 if true
    //2 if doesn't require a print afterwards
    public abstract int used(Room currentRoom, Player player);


    public int usedWith(Item item, Room currentRoom, Player player) {
        for (String s: requiredType){
            if (item.hasProperty(s)){
                return used(currentRoom, player);
            }
        }
        return 0;
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

    public void addType(String behaviour){
        properties.add(behaviour);
    }

    public boolean hasType(String property){
        for (String s : requiredType){
            if (s.equals(property)){
                return true;
            }
        }
        return false;
    }


    public boolean canTake() {
        return canTake;
    }

    public void setCanTake(boolean canTake) {
        this.canTake = canTake;
    }


    @Override
    public String getName() {
        if(customName || Material.stringify(material).equals("undefined")){
            return name.toLowerCase();
        }
        return Material.stringify(this.material)+" "+name.toLowerCase();
    }

    public void setCustomName() {
        this.customName=true;
    }

    @Override
    public void setShortDescription(String shortDescription) {
        this.customName=true;
        super.setShortDescription(shortDescription);
    }

}
