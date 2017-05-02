package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Behaviours.*;
import com.al0ne.Engine.Size;

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

    public ChargeItem(String id, String name, String description, String shortDescription,
                      double weight, Size size, Material material, int maxCharges, String onRefill) {
        super(id, name, description, shortDescription, weight, size, material);
        this.maxCharges = maxCharges;
        this.currentCharges = maxCharges;
        this.canRecharge = false;
        this.onRefill = onRefill;
    }

    public ChargeItem(String id, String name, String description, String shortDescription,
                      double weight, Size size, Material material, int maxCharges, String property, String onRefill) {
        super(id, name, description, shortDescription, weight, size, material);
        this.maxCharges = maxCharges;
        this.currentCharges = maxCharges;
        this.canRecharge = true;
        this.property = property;
        this.onRefill = onRefill;
    }

    public boolean refill(Player player, Entity entity){
        if(canRecharge && !requiresProperty){
            printToLog(onRefill);
            currentCharges = maxCharges;
            return true;
        } else if (canRecharge && (entity.getType() == 'i' || entity.getType() == 'p')){
            if(entity.getType() == 'i'){
                Item item = (Item) entity;
                if (item.hasProperty(property)){
                    currentCharges = maxCharges;
                    printToLog(onRefill);
                    return true;
                }
                return false;
            } else{
                Prop item = (Prop) entity;
                if (item.hasProperty(property)){
                    currentCharges = maxCharges;
                    printToLog(onRefill);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
