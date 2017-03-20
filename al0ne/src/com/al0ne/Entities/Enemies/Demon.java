package com.al0ne.Entities.Enemies;

import com.al0ne.Entities.Enemy;
import com.al0ne.Entities.Player;
import com.al0ne.Items.Items.Coin;
import com.al0ne.Room;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 15/03/2017.
 */
public class Demon extends Enemy{

    public Demon() {
        super("boss", "Hellish demon", "A huge horned demon with snake skin.", "demon", 20, 3);
        addItemLoot(new Coin(), 100);
        addResistance("fists");
        addResistance("sharp");
    }

    @Override
    public boolean isAttacked(Player player, Room room) {
        if(!alive){
            printToLog("You defeated the "+ name);
            printToLog("The "+name+" drops some items.");
            addLoot(room);
            player.getCurrentRoom().unlockDirection("boss");
            printToLog("You feel the magical barrier waning.");
            return true;
        }
        printToLog("The "+name+" attacks and hits you.");
        player.modifyHealthPrint(-damage);
        return false;
    }
}
