package com.al0ne.Entities.Enemies;

import com.al0ne.Entities.Enemy;
import com.al0ne.Entities.Player;
import com.al0ne.Items.Items.Coin;
import com.al0ne.Room;

/**
 * Created by BMW on 15/03/2017.
 */
public class Demon extends Enemy{

    public Demon() {
        super("boss", "Hellish demon", "A huge horned demon with snake skin.", 20, 3);
        addItemLoot(new Coin(), 100);
        addResistance("fists");
        addResistance("sharp");
    }

    @Override
    public boolean isAttacked(Player player, Room room) {
        if(!alive){
            System.out.println("You defeated the "+ name);
            System.out.println("The "+name+" drops some items.");
            addLoot(room);
            player.getCurrentRoom().unlockDirection("west");
            System.out.println("You feel the magical barrier waning.");
            return true;
        }
        System.out.println("The "+name+" attacks and hits you.");
        player.modifyHealth(-damage);
        return false;
    }
}
