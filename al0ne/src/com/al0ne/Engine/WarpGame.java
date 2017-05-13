package com.al0ne.Engine;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.World;
import com.al0ne.Entities.Worlds.AlphaWorld;
import com.al0ne.Entities.Worlds.CaveWorld;
import com.al0ne.Entities.Worlds.MedievalYoungWorld;

/**
 * Created by BMW on 24/04/2017.
 */
public class WarpGame extends Game{
    private boolean warpstone;

    public WarpGame(boolean needs) {
        super();
        this.warpstone = false;

        World startingWorld = new AlphaWorld();
        World alphaWorld = new MedievalYoungWorld();

//        World startingWorld = new MedievalYoungWorld();
//        World alphaWorld = new AlphaWorld();


        World caveWorld = new CaveWorld();
        addWorld(startingWorld);
        addWorld(caveWorld);
        addWorld(alphaWorld);

        this.currentWorld = startingWorld.getWorldName();
        this.startingWorld = startingWorld.getWorldName();
    }

    public boolean hasWarpstone() {
        return warpstone;
    }

    public void setWarpstone() {
        this.warpstone = true;
    }
}
