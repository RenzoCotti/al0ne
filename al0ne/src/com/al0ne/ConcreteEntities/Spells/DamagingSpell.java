package com.al0ne.ConcreteEntities.Spells;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class DamagingSpell extends TargetSpell {
    protected int damage;
    protected String damageType;

    public DamagingSpell(String id, String name, String description, int damage, char target, String damageType) {
        super(id, name, description, target);
        this.damage = damage;
        this.damageType = damageType;
    }
}
