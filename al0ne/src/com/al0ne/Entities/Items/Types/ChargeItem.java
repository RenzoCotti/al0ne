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
    protected String propertyRequired;
    protected String onRefill;

    public ChargeItem(String id, String name, String description,
                      double weight, Size size, Material material, int maxCharges) {
        super(id, name, description, weight, size, material, null);
        this.maxCharges = maxCharges;
        this.currentCharges = maxCharges;
        this.canRecharge = false;
    }

    public void setRefillable(String property, String onRefill){
        this.canRecharge = true;
        this.onRefill = onRefill;
        this.propertyRequired = property;
    }

    public int refill(Player player, Entity entity){
        if(!canRecharge){
            return 0;
        } else if (entity.getType() == 'i' || entity.getType() == 'p'){
            if(entity.getType() == 'i'){
                Item item = (Item) entity;
                if (item.hasProperty(propertyRequired)){
                    currentCharges = maxCharges;
                    printToLog(onRefill);
                    return 1;
                }
                return 0;
            } else{
                Prop item = (Prop) entity;
                if (item.hasProperty(propertyRequired)){
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
