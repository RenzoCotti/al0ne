package com.al0ne.Entities.Spells.ConcreteSpells;

import com.al0ne.Behaviours.Enemy;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Behaviours.Player;
import com.al0ne.Entities.Spells.DamagingSpell;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/04/2017.
 */
public class Fireball extends DamagingSpell {
    public Fireball() {
        super("fireball", "Fireball", "The ability to summon fire and throw it at your enemies", 6, 'e', "fire");
    }


    @Override
    public boolean isCasted(Player player, Entity entity) {
        if(canCastOn(entity.getType())){
            Enemy enemy = (Enemy) entity;
            if(enemy.isWeakAgainst(damageType)){
                enemy.modifyHealth(-damage);
                printToLog("You hit the "+enemy.getName()+" with a fireball.");
                enemy.isAttacked(player, player.getCurrentRoom());
                return true;
            } else {
                printToLog("The "+enemy.getName()+" resists the flames.");
                return true;
            }
        } else{
            printToLog("You can't cast a fireball at it.");
            return false;
        }
    }
}
