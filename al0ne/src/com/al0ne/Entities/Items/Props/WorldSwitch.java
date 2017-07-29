package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.GameChanges;

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
    public int used(Room currentRoom, Player player) {
        GameChanges.changeWorld(nextWorld);
        return 2;
    }
}
