package com.al0ne.Engine;

import com.al0ne.Behaviours.Area;
import com.al0ne.Behaviours.Enums.TechLevel;
import com.al0ne.Entities.Areas.DeltaBlock;
import com.al0ne.Entities.Areas.TestingArea;
import com.al0ne.Entities.Areas.FirstAlphaArea;
import com.al0ne.Entities.Areas.VillageArea;

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
