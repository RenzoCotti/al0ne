package com.al0ne.ConcreteEntities.Items.Props.Types.MoveProps;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.Engine.Utility.GameChanges;

/**
 * Created by BMW on 23/07/2017.
 * this completely switches world
 */
public class WorldSwitch extends Prop{
    protected int nextWorld;

    public WorldSwitch(String name, String description, int nextWorld) {
        super(name, description);
        this.nextWorld = nextWorld;
    }

    @Override
    public boolean used(Player player) {
        //TODO fix world switch prop, unsafe atm

//        GameChanges.changeWorld(nextWorld);
        return true;
    }
}
