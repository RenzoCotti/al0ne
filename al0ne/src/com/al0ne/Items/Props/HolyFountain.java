package com.al0ne.Items.Props;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Prop;
import com.al0ne.Room;

/**
 * Created by BMW on 14/03/2017.
 */
public class HolyFountain extends Prop{
    public HolyFountain() {
        super("holyf", "Holy Fountain", "The water in this decorated fountain seems so clear and refreshing");
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        System.out.println("You are fully healed!");
        player.setHealth(player.getMaxHealth());
        return true;
    }
}
