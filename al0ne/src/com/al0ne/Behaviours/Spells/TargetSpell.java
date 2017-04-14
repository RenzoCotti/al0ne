package com.al0ne.Behaviours.Spells;

import com.al0ne.Behaviours.Entity;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Spell;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class TargetSpell extends Spell {
    public TargetSpell(String id, String name, String description, char target) {
        super(id, name, description);
        this.target = target;
    }
    public abstract boolean isCasted(Player player, Entity entity);
}
