package com.al0ne.Entities.Items.Types;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.abstractEntities.Entity;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public abstract class ChargeItem extends Item{

    protected int maxCharges;
    protected int currentCharges;
    protected boolean canRecharge;
    protected boolean requiresProperty;
    protected String property;
    protected String onRefill;

    public ChargeItem(String id, String name, String description,
                      double weight, Size size, Material material, int maxCharges) {
        super(id, name, description, weight, size, material, null);
        this.maxCharges = maxCharges;
        this.currentCharges = maxCharges;
        this.canRecharge = false;
    }

    public ChargeItem(String id, String name, String description,
                      double weight, Size size, Material material, int maxCharges, String property, String onRefill) {
        super(id, name, description, weight, size, material, null);
        this.maxCharges = maxCharges;
        this.currentCharges = maxCharges;
        this.canRecharge = true;
        this.property = property;
        this.onRefill = onRefill;
    }

    public int refill(Player player, Entity entity){
        System.out.println("here");
        if(canRecharge && !requiresProperty){
            System.out.println("here1");
            printToLog(onRefill);
            currentCharges = maxCharges;
            return 1;
        } else if (canRecharge && (entity.getType() == 'i' || entity.getType() == 'p')){
            if(entity.getType() == 'i'){
                Item item = (Item) entity;
                if (item.hasProperty(property)){
                    currentCharges = maxCharges;
                    printToLog(onRefill);
                    return 1;
                }
                return 0;
            } else{
                Prop item = (Prop) entity;
                if (item.hasProperty(property)){
                    currentCharges = maxCharges;
                    printToLog(onRefill);
                    return 1;
                }
                return 0;
            }
        }
        return 0;
    }

    public int getMaxCharges() {
        return maxCharges;
    }

    public void setMaxCharges(int maxCharges) {
        this.maxCharges = maxCharges;
    }

    public int getCurrentCharges() {
        return currentCharges;
    }

    public boolean removeOneCharge() {
        this.currentCharges --;
        if(currentCharges <= 0){
            return true;
        }
        return false;
    }
}
