package com.al0ne.Entities.Enemies;

import com.al0ne.Behaviours.Enemy;
import com.al0ne.Behaviours.Player;
import com.al0ne.Entities.Items.ConcreteItems.Coin.SilverCoin;
import com.al0ne.Behaviours.Room;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 15/03/2017.
 */
public class Demon extends Enemy {

    public Demon() {
        super("boss", "Hellish demon", "A huge horned demon with snake skin.", "a demon",
                20, 40, 10, 3, 3);
        addItemLoot(new SilverCoin(), 100, 100);
        addResistance("fists");
        addResistance("sharp");
    }

    @Override
    public void isAttacked(Player player, Room room) {
        if(!alive){
            printToLog("You defeated the "+ name);
            printToLog("The "+name+" drops some items.");
            addLoot(room);
            player.getCurrentRoom().unlockDirection("boss");
            printToLog("You feel the magical barrier waning.");
            return;
        }
        printToLog("The "+name+" attacks and hits you.");
        //atm it bypasses armor and attack rolls
        player.modifyHealth(-damage);
    }
}
