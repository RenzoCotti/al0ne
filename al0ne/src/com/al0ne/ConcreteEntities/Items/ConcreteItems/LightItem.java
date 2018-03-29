package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.ConcreteEntities.Items.Types.ChargeItem;

import static com.al0ne.Engine.Main.printToLog;

public class LightItem extends ChargeItem{

    protected boolean isLit;

    public LightItem(String id, String name, String description,
                     double weight, Size size, Material material, int maxCharges) {
        super(id, name, description, weight, size, material, maxCharges);
        this.isLit = false;
        this.addCommand(Command.LIGHT);
    }

    @Override
    public boolean used(Player player) {
        if(this.currentCharges > 0 && !this.isLit){
            this.isLit = true;
            return true;
        } else {
            this.isLit = false;
            return false;
        }
    }

    public boolean isLit() {
        return isLit;
    }

    public void setLit(boolean lit) {
        this.isLit = lit;
    }

    @Override
    public void printLongDescription(Player player) {
        super.printLongDescription(player);
        if(isLit){
            printToLog("It is lighted.");
        } else{
            printToLog("It isn't lighted.");
        }
    }
}
