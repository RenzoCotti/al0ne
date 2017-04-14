package com.al0ne.Behaviours.Spells;

import com.al0ne.Behaviours.Spell;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class DamagingSpell extends TargetSpell {
    protected int damage;
    protected String damageType;
    protected String onCast;

    public DamagingSpell(String id, String name, String description, int damage, char target, String onCast, String damageType) {
        super(id, name, description, target);
        this.damage = damage;
        this.onCast = onCast;
        this.damageType = damageType;
    }
}
