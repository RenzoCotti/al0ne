package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Status;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.ChargeItem;
import com.al0ne.Entities.Statuses.Thirsty;

import java.util.Iterator;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public class Canteen extends ChargeItem{
    public Canteen() {
        super("canteen", "Canteen", "A canteeen made with the bladder of an animal. Holds about 5 sips.", "a leather canteen", 0.4, Size.SMALL, 5, "water", "You refill your canteen.");
        this.addCommand("drink");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {

        if(currentCharges <= 0){
            printToLog("It's empty.");
            return false;
        }
        if (player.hasStatus("dehydrated")){
            player.removeStatus("dehydrated");
            printToLog("Finally some fresh water!");
        } else{
            Thirsty thirsty = (Thirsty) player.getStatus().get("thirsty");
            thirsty.setDuration(thirsty.THIRST_CLOCK);
        }
        currentCharges--;
        return true;
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
}
