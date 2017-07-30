package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Prop;

public class Lightswitch extends Prop{
    public Lightswitch(String name, String description) {
        super(name, description);
        addCommand(Command.PRESS);
    }

    @Override
    public String used(Player player) {
        player.getCurrentRoom().setLit(true);

        return "It gets brighter.";
    }
}
