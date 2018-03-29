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

    protected int integrity;

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

    public void setUndroppable() {
        this.canDrop = false;
    }


    public boolean canDrop(){
        return canDrop;
    }

    //returns null if can't use
    //String if needs printing
    //"" if no need for print
    public String used(Player player){
        return null;
    }


    public void usedWith(Interactable inter, Player player) {

        Physics.interactionBetween(player, this, inter);

    }

//    public void useResult(HashMap<Integer, Object> result, Player player, Behaviour interacted, Interactable inter){
//
//        Room currentRoom = player.getCurrentRoom();
//        for(Integer i : result.keySet()){
//            switch (i){
//
//                case 1:
//                    //success, no need to print
//                    break;
//                case 3:
//                    //tries to add to inventory, if can't add to room
//                    Pair pair = interacted.getEntity().getPair();
//                    if(player.addAllItem(pair)){
//                        break;
//                    }
//                case 2:
//                    //add to room
//                    Pair pair1 = interacted.getEntity().getPair();
//                    Entity entity = pair1.getEntity();
//                    int count = pair1.getCount();
//                    currentRoom.addEntity(entity, count);
//                    break;
//                case 4:
//                    //remove this
//                    if(player.hasItemInInventory(this.getID())){
//                        player.removeOneItem((Item) this);
//                    } else{
//                        currentRoom.getEntities().remove(this.getID());
//                    }
//                    break;
//                case 5:
//                    //remove other
//                    if(player.hasItemInInventory(inter.getID())){
//                        player.removeOneItem((Item) inter);
//                    } else{
//                        currentRoom.getEntities().remove(inter.getID());
//                    }
//                    break;
//                case 6:
//                    currentRoom.unlockDirection((String)result.get(i));
//                    break;
//                case 7:
//                    //checkRefill
//                    ((ChargeItem) this).checkRefill(player, inter);
//                    break;
//                case 8:
//                    //modify health
//                    player.modifyHealth((Integer)result.get(i));
//                    break;
//                case 9:
//                    //modify integrity
//                    this.modifyIntegrity((Integer) result.get(i));
//                    break;
//
//                default:
//                    System.out.println("ERROR: no behaviour code found");
//                    break;
//
//            }
//        }
//    }

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
