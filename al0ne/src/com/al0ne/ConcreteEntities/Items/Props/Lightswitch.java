package com.al0ne.ConcreteEntities.Items.Props;

import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;

public class Lightswitch extends Prop{
    public Lightswitch(String name, String description) {
        super(name, description);
        addCommand(Command.PRESS);
    }

    @Override
    public String used(Player player) {

        if(player.getCurrentRoom().isLit()){
            player.getCurrentRoom().setLit(false);
            return "It gets darker.";
        }
        player.getCurrentRoom().setLit(true);
        player.getCurrentRoom().printRoom();
        return "It gets brighter.";
    }
}
