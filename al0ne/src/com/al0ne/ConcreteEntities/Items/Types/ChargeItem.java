package com.al0ne.ConcreteEntities.Items.Types;

import com.al0ne.AbstractEntities.*;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.AbstractEntities.Abstract.Item;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public abstract class ChargeItem extends Item {

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

    public int checkRefill(Player player, Entity entity){
        if(!canRecharge){
            return 0;
        } else if(entity instanceof Item){
                Item item = (Item) entity;
                if (item.hasProperty(propertyRequired)){
                    currentCharges = maxCharges;
                    printToLog(onRefill);
                    return 1;
                }
                return 0;
        } else if(entity instanceof Prop){
            Prop item = (Prop) entity;
            if (item.hasProperty(propertyRequired)){
                currentCharges = maxCharges;
                printToLog(onRefill);
                return 1;
            }
            return 0;
        }
        return 0;
    }

    public int refill(){
        this.currentCharges = maxCharges;
        return 1;
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
