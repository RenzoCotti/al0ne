package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Pairs.Prop;
import com.al0ne.Behaviours.Room;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/03/2017.
 */
public class HolyFountain extends Prop{
    public HolyFountain() {
        super("holyf", "Holy Fountain", "The water in this decorated fountain seems so clear and refreshing", "water fountain");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        printToLog("You are fully healed!");
        player.setHealth(player.getMaxHealth());
        return true;
    }
}
