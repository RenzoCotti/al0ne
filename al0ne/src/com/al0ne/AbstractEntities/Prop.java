package com.al0ne.AbstractEntities;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Interactable;
import com.al0ne.Engine.Utility.Utility;

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

    private String afterDescription;
    private String requiresItem;
    protected boolean active;
    protected boolean invisible;

    public Prop(String name, String description, String shortDescription, String after, Material material) {
        super("prop"+(entityCounter++), name, description, shortDescription, material);
        setCustomName();

        if(after == null){
            this.afterDescription = description;
        } else{
            this.afterDescription = after;
        }

        this.requiresItem="none";
        this.active=false;
        this.canTake=false;
        this.invisible=false;
    }


    //quick, for descriptive props
    public Prop(String name, String description, Material m) {
        super("prop"+(entityCounter++), name, description,
                Utility.getArticle(name)+" "+name.toLowerCase(), m);
        this.afterDescription = description;
        this.requiresItem="none";
        this.active=false;
        this.canTake=false;
        this.invisible=false;

    }

    //quick, for descriptive props
    public Prop(String name, String description, String shortDesc) {
        super("prop"+(entityCounter++), name, description, shortDesc, Material.UNDEFINED);
        this.afterDescription = description;
        this.requiresItem="none";
        this.active=false;
        this.canTake=false;
        this.invisible=false;
    }

    //quick, for descriptive props
    public Prop(String name, String description) {
        super("prop"+(entityCounter++), name, description,
                Utility.getArticle(name)+" "+name.toLowerCase(), Material.UNDEFINED);
        this.afterDescription = description;
        this.requiresItem="none";
        this.active=false;
        this.canTake=false;
        this.invisible=false;

    }

    //constructors for door
    public Prop(String doorID, String doorName, String description, String shortDescription, String after, Material m) {
        super(doorID, doorName, description, shortDescription, m);
        setCustomName();
        this.afterDescription = after;
        this.requiresItem="none";
        this.active=false;
        this.canTake=false;
        this.invisible=false;
    }


    @Override
    public boolean used(Player player){
        if (requiresItem.equals("none")){
            active=true;
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void printLongDescription(Player player){
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

    public boolean isInvisible() {
        return invisible;
    }
}
