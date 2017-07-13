package com.al0ne.Behaviours.abstractEntities;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Physics.Behaviour;
import com.al0ne.Entities.Items.Behaviours.ChargeItem;

import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 06/05/2017.
 */
public abstract class Interactable extends Entity {
    //todo: add plural?

    protected ArrayList<Behaviour> properties;


    protected boolean canDrop;
    public boolean canTake;

    public int integrity;

    protected Material material;
    public boolean customName=false;


    public Interactable(String id, String name, String description, String shortDescription, Material m) {
        super(id, name, description, shortDescription);
        this.properties = new ArrayList<>();

        this.integrity = 100;
        if (m == null){
            this.material = Material.UNDEFINED;
        } else{
            this.material = m;
        }
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


    public void usedWith(Interactable inter, Room currentRoom, Player player) {
        String result = "0";
        Behaviour interacted = null;
        for (Behaviour b: properties){
            for(Behaviour b1: inter.getProperties()){
                result = b.isInteractedWith(b1);
                if(!result.equals("0")){
                    interacted = b;
                    break;
                }
            }
            if(!result.equals("0")){
                break;
            }
        }

        if(interacted == null){
            printToLog("The "+inter.getName()+" isn't effective");
            return;
        }

        char [] temp = result.toCharArray();

        for(char c : temp){
            switch (c){

                case '1':
                    //success, no need to print
                    break;
                case '3':
                    //tries to add to inventory, if can't add to room
                    Pair pair = interacted.getEntity().getPair();
                    System.out.println(interacted.getName());
                    if(player.addAllItem(pair)){
                        break;
                    }
                case '2':
                    //add to room
                    Pair pair1 = interacted.getEntity().getPair();
                    Entity entity = pair1.getEntity();
                    int count = pair1.getCount();
                    currentRoom.addEntity(entity, count);
                    break;
                case '4':
                    //remove this
                    if(player.hasItemInInventory(this.getID())){
                        player.removeOneItem((Item) this);
                    } else{
                        currentRoom.getEntities().remove(this.getID());
                    }
                    break;
                case '5':
                    //remove other
                    if(player.hasItemInInventory(inter.getID())){
                        player.removeOneItem((Item) inter);
                    } else{
                        currentRoom.getEntities().remove(inter.getID());
                    }
                    break;
                case '6':
                    currentRoom.unlockDirection(interacted.getLock());
                    break;
                case '7':
                    //refill
                    ((ChargeItem) this).refill(player, inter);
                    break;
                case '8':
                    //modify health
                    player.modifyHealth(interacted.getHealthModifier());
                    break;

            }
        }


    }

    public void addProperty(Behaviour behaviour){
        properties.add(behaviour);
    }

    public boolean hasProperty(String property){
        for (Behaviour b : properties){
            if (b.getName().equals(property)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Behaviour> getProperties() {
        return properties;
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

    public String getRootName() {
        return name.toLowerCase();
    }

    public void setCustomName() {
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
        return integrity;
    }

    //returns false if the item gets destroyed
    public boolean modifyIntegrity(int amount) {
        if(this.integrity + amount <= 0){
            return false;
        } if(this.integrity + amount > 100){
            this.integrity = 100;
        } else{
            this.integrity += integrity;
        }
        return true;
    }
}
