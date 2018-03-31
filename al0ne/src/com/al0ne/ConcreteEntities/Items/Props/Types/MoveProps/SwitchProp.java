package com.al0ne.ConcreteEntities.Items.Props.Types.MoveProps;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.AbstractEntities.Room;

import static com.al0ne.Engine.Main.printToLog;

public abstract class SwitchProp extends Prop {
    protected String teleportMessage;

    public SwitchProp(String name, String description) {
        super(name, description);
        teleportMessage = "You are now in another area";
    }
    public SwitchProp(String name, String description, String s) {
        super(name, description);
        teleportMessage = s;
    }

    public abstract boolean teleport(Player p);


    @Override
    public boolean used(Player player) {
        return teleport(player);
    }
}
