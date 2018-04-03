package com.al0ne.ConcreteEntities.Enemies;

import com.al0ne.AbstractEntities.Abstract.Enemy;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.Coin;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 15/03/2017.
 */
public class Demon extends Enemy {

    public Demon() {
        super("Hellish demon", "A huge horned demon with snake skin.", "a demon",
                20, 40, 10, 3, 3);
        addItemLoot(new Coin(), 100, 100);
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
            return false;
        }
        printToLog("The "+name+" attacks and hits you.");
        //atm it bypasses armor and attack rolls
        player.modifyHealth(-damage);
        return true;
    }
}
