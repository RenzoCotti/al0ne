package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/03/2017.
 */
public class HolyFountain extends Prop{
    public HolyFountain() {
        super("Holy Fountain",
                "The water in this decorated fountain seems so clear and refreshing",
                "water fountain", null, Material.STONE);
    }

    @Override
    public int used(Room currentRoom, Player player) {
        printToLog("You are fully healed!");
        player.setHealth(player.getMaxHealth());
        return 1;
    }
}
