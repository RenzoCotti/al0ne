package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Entity;
import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Items.Behaviours.ChargeItem;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Entities.Statuses.ConcreteStatuses.Thirst;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public class Canteen extends ChargeItem{
    public Canteen() {
        super("canteen", "Canteen",
                "A canteeen made with the bladder of an animal.",
                0.6, Size.SMALL, Material.LEATHER, 5,
                "water", "You refill your canteen.");
        this.addCommand(Command.DRINK);
        setUnique();
    }

    @Override
    public int used(Room currentRoom, Player player) {

        if(currentCharges <= 0){
            printToLog("It's empty.");
            return 0;
        }
        if (player.hasStatus("dehydrated")){
            player.removeStatus("dehydrated");
            printToLog("Finally some fresh water!");
        } else{
            Thirst thirst = (Thirst) player.getStatus().get("thirst");
            thirst.setDuration(Thirst.THIRST_CLOCK);
        }
        currentCharges--;
        modifyWeight(-0.1);
        player.recalculateWeight();
        return 1;
    }

    @Override
    public void printLongDescription(Player player, Room room) {
        printToLog(longDescription+" "+getChargesString());
    }


    private String getChargesString(){
        int chargesLeft = (int) (((double)currentCharges/(double)maxCharges) * 100);

        if ( chargesLeft > 70 ){
            return "It's practically full.";
        } else if( chargesLeft > 40) {
            return "It's half full.";
        } else if (chargesLeft > 0){
            return "It's almost empty.";
        } else{
            return "It's empty";
        }
    }

    @Override
    public boolean refill(Player player, Entity entity) {
        if(super.refill(player, entity)){
            //bug, can overfill if already full for weight
            weight=0.6;
            player.recalculateWeight();
            return true;
        }
        return false;
    }
}
