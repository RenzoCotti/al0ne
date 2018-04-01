package com.al0ne.ConcreteEntities.Spells;

import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.AbstractEntities.Player.Player;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class TargetSpell extends Spell {
    public TargetSpell(String id, String name, String description, Class c) {
        super(id, name, description);
        this.target = c;
    }
    public abstract boolean isCasted(Player player, Entity entity);
}
