package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.ChargeItem;

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
public class Prop extends Interactable {

    protected String afterDescription;
    protected String requiresItem;
    protected boolean active;



    //detailed prop creation
    public Prop(String name, String description, String shortDescription, Material m) {
        super("prop", name, description, shortDescription, m);
        setCustomName();
        this.afterDescription = description;
        this.requiresItem="none";
        this.active=false;
        this.type='p';
        this.canTake=false;
    }

    public Prop(String name, String description, String shortDescription, String after, Material m) {
        super("prop", name, description, shortDescription, m);
        setCustomName();
        this.afterDescription = after;
        this.requiresItem="none";
        this.active=false;
        this.type='p';
        this.canTake=false;
    }


    //constructors for door
    public Prop(String doorID, String doorName, String description, String shortDescription, String after, Material m) {
        super(doorID, doorName, description, shortDescription, m);
        setCustomName();
        this.afterDescription = after;
        this.requiresItem="none";
        this.active=false;
        this.type='p';
        this.canTake=false;
    }


    //quick, for descriptive props
    public Prop(String name, String description) {
        super("prop", name, description, Utility.getArticle(name)+" "+name.toLowerCase(), Material.UNDEFINED);
        this.afterDescription = description;
        this.requiresItem="none";
        this.active=false;
        this.type='p';
        this.canTake=false;
    }

    public Prop(String name, String description, String shortDescription) {
        super("prop", name, description, shortDescription, Material.UNDEFINED);
        setCustomName();
        this.afterDescription = description;
        this.requiresItem="none";
        this.active=false;
        this.type='p';
        this.canTake=false;
    }





    @Override
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
            return charge.refill(player, this);
        }
        return false;
    }


    @Override
    public int used(Room currentRoom, Player player){
        if (requiresItem.equals("none")){
            active=true;
            return 1;
        } else{
            return 0;
        }
    }

    @Override
    public void printLongDescription(Player player, Room room){
        if(!active){
            printToLog(longDescription);
        } else {
            printToLog(afterDescription);
        }
        String m = Material.stringify(this.material);
        if(!m.equals("undefined")){
            printToLog("It's made of "+(Material.stringify(this.material))+".");
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
