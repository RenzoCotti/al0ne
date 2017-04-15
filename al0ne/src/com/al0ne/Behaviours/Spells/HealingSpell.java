package com.al0ne.Behaviours.Spells;

import com.al0ne.Behaviours.Player;

/**
 * Created by BMW on 15/04/2017.
 */
public abstract class HealingSpell extends SelfSpell{
    protected int healing;
    protected String onCast;

    public HealingSpell(String id, String name, String description, int healing) {
        super(id, name, description);
        this.healing = healing;
        this.onCast = "You are healed";
    }
}
