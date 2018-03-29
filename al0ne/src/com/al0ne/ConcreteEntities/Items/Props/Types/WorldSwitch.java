package com.al0ne.ConcreteEntities.Items.Props.Types;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.Engine.Utility.GameChanges;

/**
 * Created by BMW on 23/07/2017.
 */
public class WorldSwitch extends Prop{
    protected String nextWorld;

    public WorldSwitch(String name, String description, String nextWorld) {
        super(name, description);
        this.nextWorld = nextWorld;
    }

    @Override
    public String used(Player player) {
        GameChanges.changeWorld(nextWorld);
        return "";
    }
}
