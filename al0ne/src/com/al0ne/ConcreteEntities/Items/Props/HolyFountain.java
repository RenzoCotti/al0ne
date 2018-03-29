package com.al0ne.ConcreteEntities.Items.Props;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.Engine.Physics.Behaviours.WaterBehaviour;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/03/2017.
 */
public class HolyFountain extends Prop{
    public HolyFountain() {
        super("Holy Fountain",
                "The water in this decorated fountain seems so clear and refreshing",
                "water fountain", null, Material.STONE);
        addBehaviour(new WaterBehaviour());
    }

    @Override
    public boolean used(Player player) {
        printToLog("You are fully healed!");
        player.setHealth(player.getMaxHealth());
        return true;
    }
}
