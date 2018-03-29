package com.al0ne.Engine.Game;

import com.al0ne.AbstractEntities.Area;
import com.al0ne.ConcreteEntities.Areas.TestingArea;
import com.al0ne.ConcreteEntities.Areas.FirstAlphaArea;
import com.al0ne.ConcreteEntities.Areas.VillageArea;

/**
 * Created by BMW on 24/04/2017.
 */
public class WarpGame extends Game{
    private boolean warpstone;

    public WarpGame() {
        super("warpgame");
        this.warpstone = false;


        Area startingArea = new TestingArea();
        Area alphaWorld = new TestingArea();


        Area villageArea = new VillageArea();
        Area caveWorld = new FirstAlphaArea();
        addWorld(startingArea);
        addWorld(villageArea);
        addWorld(caveWorld);
        addWorld(alphaWorld);

        this.currentWorld = startingArea.getAreaName();
        this.startingWorld = startingArea.getAreaName();
    }

    public boolean hasWarpstone() {
        return warpstone;
    }

    public void setWarpstone() {
        this.warpstone = true;
    }
}
