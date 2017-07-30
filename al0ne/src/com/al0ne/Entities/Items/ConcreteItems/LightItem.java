package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Entities.Items.Types.ChargeItem;

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
    public int used(Room currentRoom, Player player) {
        if(this.currentCharges > 0 && !this.isLit){
            this.isLit = true;
            return 1;
        } else {
            this.isLit = false;
            return 0;
        }
    }

    public boolean isLit() {
        return isLit;
    }

    public void setLit(boolean lit) {
        this.isLit = lit;
    }

    @Override
    public void printLongDescription(Player player, Room room) {
        super.printLongDescription(player, room);
        if(isLit){
            printToLog("It is lighted.");
        } else{
            printToLog("It isn't lighted.");
        }
    }
}
