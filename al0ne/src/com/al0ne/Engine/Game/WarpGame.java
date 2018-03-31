package com.al0ne.Engine.Game;

import com.al0ne.AbstractEntities.World;
import com.al0ne.ConcreteEntities.Worlds.CyberpunkWorld;
import com.al0ne.ConcreteEntities.Worlds.MedievalWorld;

/**
 * Created by BMW on 24/04/2017.
 */
public class WarpGame extends Game{
    private boolean warpstone;

    public WarpGame() {
        super("warpgame");
        this.warpstone = false;



        World medievalWorld = new MedievalWorld();

        World cyberPunkWorld = new CyberpunkWorld();

        addWorld(medievalWorld);
        addWorld(cyberPunkWorld);




        this.currentWorld = 0;
        this.startingWorld = 0;
    }

    public boolean hasWarpstone() {
        return warpstone;
    }

    public void setWarpstone() {
        this.warpstone = true;
    }
}
