package com.al0ne.AbstractEntities.Abstract;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.Engine.Physics.Behaviour;
import com.al0ne.Engine.Physics.Physics;

import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 06/05/2017.
 */
public abstract class Interactable extends Entity {
    //todo: add plural?

    protected ArrayList<Behaviour> behaviours;


    protected boolean canDrop;
    protected boolean canTake;

    private int integrity;

    protected Material material;
    protected boolean customName=false;


    public Interactable(String id, String name, String description, String shortDescription, Material m) {
        super(id, name, description, shortDescription);
        this.behaviours = new ArrayList<>();

        this.integrity = 100;
        if (m == null){
            this.material = Material.UNDEFINED;
        } else{
            this.material = m;
        }
    }

    protected void setUndroppable() {
        this.canDrop = false;
    }


    public boolean canDrop(){
        return canDrop;
    }

    //returns null if can't use
    //String if needs printing
    //"" if no need for print
    public boolean used(Player player){
        return false;
    }


    public void usedWith(Interactable inter, Player player) {

        Physics.interactionBetween(player, this, inter);

    }

    public void addBehaviour(Behaviour behaviour){
        behaviours.add(behaviour);
    }

    public boolean hasProperty(String property){
        for (Behaviour b : behaviours){
            if (b.getName().equals(property)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Behaviour> getBehaviours() {
        return behaviours;
    }


    public boolean canTake() {
        return canTake;
    }

    public void setCanTake(boolean canTake) {
        this.canTake = canTake;
    }


//    @Override
//    public String getName() {
//        if(customName || Material.stringify(material).equals("undefined")){
//            return name.toLowerCase();
//        }
//        return Material.stringify(this.material)+" "+name.toLowerCase();
//    }

    public String getRootName() {
        return name.toLowerCase();
    }

    protected void setCustomName() {
        this.customName=true;
    }

    @Override
    public void setShortDescription(String shortDescription) {
        this.customName=true;
        super.setShortDescription(shortDescription);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getIntegrity() {
        return this.integrity;
    }

    //returns false if the item gets destroyed
    public void modifyIntegrity(int amount) {
        if(this.integrity + amount >= 100){
            this.integrity = 100;
        } else{
            this.integrity += amount;
        }
    }

    public void setIntegrity(int integrity) {
        this.integrity = integrity;
    }
}
