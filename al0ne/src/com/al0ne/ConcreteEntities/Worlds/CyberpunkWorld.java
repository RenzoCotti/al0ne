package com.al0ne.ConcreteEntities.Worlds;

import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.World;
import com.al0ne.ConcreteEntities.Areas.DeltaBlock;
import com.al0ne.ConcreteEntities.Statuses.DeathStatus;

public class CyberpunkWorld extends World{
    public CyberpunkWorld() {
        super("cyberpunk", TechLevel.HIGH, new Player("You don't remember anything about you.",
                true, 20, 40, 40, 0, 1, 20));

        player.addStatus(new DeathStatus("Kidney failure", 60, "You feel dizzy.",
                "Your kidney hurts like hell.", "You died of kidney failure."));

        DeltaBlock d = new DeltaBlock(this);
        addArea(d);

        player.setStart(d);

    }

}
