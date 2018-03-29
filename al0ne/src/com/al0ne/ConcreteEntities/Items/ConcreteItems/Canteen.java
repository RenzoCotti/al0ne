package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.ChargeItem;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses.Thirst;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public class Canteen extends ChargeItem{
    public Canteen() {
        super("canteen", "Canteen",
                "A canteeen made with the bladder of an animal.",
                0.6, Size.SMALL, Material.LEATHER, 5);
        this.addCommand(Command.DRINK);
        setRefillable("water", "You checkRefill your canteen.");
        setUnique();
    }

    @Override
    public boolean used(Player player) {

        if(currentCharges <= 0){
            printToLog("It's empty.");
            return true;
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
        return true;
    }

    @Override
    public void printLongDescription(Player player) {
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
    public int checkRefill(Player player, Entity entity) {
        if(super.checkRefill(player, entity) == 1){
            //bug, can overfill if already full for weight
            weight=0.6;
            player.recalculateWeight();
            return 1;
        }
        return 0;
    }
}
