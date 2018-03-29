package com.al0ne.ConcreteEntities.Items.Props;

import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.ConcreteEntities.Statuses.PrintStatus;

public class Lightswitch extends Prop{
    public Lightswitch(String name, String description) {
        super(name, description);
        addCommand(Command.PRESS);
    }

    @Override
    public boolean used(Player player) {

        if(player.getCurrentRoom().isLit()){
            player.getCurrentRoom().setLit(false);
            player.addStatus(new PrintStatus("It gets darker."));
            return true;
        }
        player.getCurrentRoom().setLit(true);
        player.getCurrentRoom().printRoom();
        player.addStatus(new PrintStatus("It gets brighter."));

        return true;
    }
}
