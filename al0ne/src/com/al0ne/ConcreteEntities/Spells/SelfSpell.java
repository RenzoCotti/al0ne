package com.al0ne.ConcreteEntities.Spells;

import com.al0ne.AbstractEntities.Player.Player;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class SelfSpell extends Spell{
    public SelfSpell(String id, String name, String description) {
        super(id, name, description);
        this.target = Player.class;
    }

    public abstract boolean isCasted(Player player);
}
