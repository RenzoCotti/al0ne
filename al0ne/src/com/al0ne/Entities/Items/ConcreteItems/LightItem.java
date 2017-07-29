package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Entities.Items.Types.ChargeItem;

import static com.al0ne.Engine.Main.printToLog;

public class LightItem extends ChargeItem{

    protected boolean turnedOn;

    public LightItem(String id, String name, String description,
                     double weight, Size size, Material material, int maxCharges) {
        super(id, name, description, weight, size, material, maxCharges);
        this.turnedOn = false;
        this.addCommand(Command.LIGHT);
    }

    public LightItem(String id, String name, String description,
                     double weight, Size size, Material material, int maxCharges, String property, String onRefill) {
        super(id, name, description, weight, size, material, maxCharges, property, onRefill);
        this.turnedOn = false;
        this.addCommand(Command.LIGHT);
    }

    @Override
    public int used(Room currentRoom, Player player) {
        if(this.currentCharges > 0 && !this.turnedOn){
            this.turnedOn = true;
            return 1;
        } else {
            this.turnedOn = false;
            return 0;
        }
    }

    public boolean isTurnedOn() {
        return turnedOn;
    }

    public void setTurnedOn(boolean turnedOn) {
        this.turnedOn = turnedOn;
    }

    @Override
    public void printLongDescription(Player player, Room room) {
        super.printLongDescription(player, room);
        if(turnedOn){
            printToLog("It is lighted.");
        } else{
            printToLog("It isn't lighted.");
        }
    }
}
