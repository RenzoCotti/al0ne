package com.al0ne.ConcreteEntities.Spells;

import com.al0ne.AbstractEntities.Abstract.Entity;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class DamagingSpell extends TargetSpell {
    protected int damage;
    protected String damageType;

    public DamagingSpell(String id, String name, String description, int damage, Class c, String damageType) {
        super(id, name, description, c);
        this.damage = damage;
        this.damageType = damageType;
    }
}
