package com.al0ne.ConcreteEntities.Spells;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class WorldSpell extends Spell {
    public WorldSpell(String id, String name, String description) {
        super(id, name, description);
        this.target = Room.class;
    }

    public abstract boolean isCasted(Player player, Room currentRoom);
}
